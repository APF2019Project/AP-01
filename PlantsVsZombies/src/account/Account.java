package account;

import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
import graphic.CloseButton;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Program;
import page.ErrorMessage;
import page.Form;
import page.Message;
import page.Page;
import page.menu.ActionButton;
import page.menu.Button;
import page.menu.Menu;
import util.Effect;
import util.Unit;

import javax.naming.NamingException;
import javax.security.sasl.AuthenticationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Account implements Serializable {
  private static final long serialVersionUID = -5582985394951882515L;
  private static final String BACKUP_ADDRESS = Program.getBackupPath("account.ser");
  static Account current;
  private static Map<String, Account> ALL = new HashMap<>();
  //private String passwordSalt;
  public int score;
  Store store;
  private String username;
  private String passwordHash;

  private Account(String username, String password) {
    this.username = username;
    this.passwordHash = password;
    this.store = new Store(this);
    ALL.put(username, this);
  }

  public static Account getCurrentAccount() {
    return current;
  }

  public static void backupAll() {
    try {
      FileOutputStream fileOut =
              new FileOutputStream(BACKUP_ADDRESS);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(ALL);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      ErrorMessage.from(i).action();
    }
  }

  public static Page<Unit> profilePage() {
    return new Menu<Unit>(
            new Button<Unit>() {
              @Override
              public String getLabel() {
                return "Delete account";
              }

              @Override
              public String getHelp() {
                return "This button will delete your account";
              }

              @Override
              public Effect<Unit> action() {
                Account.current.delete();
                Message.show("your account deleted successfully");
                return Effect.ok();
              }

            },
            new ActionButton<>("change",
                    (new Form("Enter username", "Enter password"))
                            .action()
                            .flatMap(data -> Effect.syncWork(() -> {
                              Account.login(data[0], data[1]);
                            }))
                            .showError()
                            .map(x -> "Account changed successfully")
                            .show()
            ),
            new ActionButton<>("rename",
                    (new Form("Enter new username"))
                            .action()
                            .flatMap(data -> Account.current.rename(data[0]))
                            .showError()
                            .map(x -> "Your username changed successfully")
                            .show()
            ),
            new ActionButton<>("create",
                    (new Form("Enter new username", "Enter password"))
                            .action()
                            .flatMap(data -> Effect.syncWork(() -> {
                              Account.create(data[0], data[1]);
                            }))
                            .map((x) -> "Account created successfully")
                            .show()
                            .showError()
            ),
            new ActionButton<>("Show", Effect.ok()
                    .map(x -> Account.current.username).show())
    );
  }

  @SuppressWarnings("unchecked")
  public static void recoverAll() {
    try {
      FileInputStream fileIn = new FileInputStream(BACKUP_ADDRESS);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      ALL = (HashMap<String, Account>) in.readObject();
      in.close();
      fileIn.close();
    } catch (Exception i) {
      ErrorMessage.from(i).action();
    }
  }

  public static Account getByUsername(String username) {
    return ALL.get(username);
  }

  public static Effect<Unit> create(String username, String password) {
    return Effect.syncWork(() -> {
      if (getByUsername(username) != null)
        throw new NamingException();
      new Account(username, password);
    });
  }

  public static Effect<Unit> login(String username, String password) {
    return Effect.syncWork(() -> {
      Account account = getByUsername(username);
      if (account == null) throw new AuthenticationException();
      if (!account.matchPassword(password)) throw new AuthenticationException();
      current = account;
    });
  }

  public static ArrayList<PlantDna> getCurrentUserPlants() {
    return Account.current.getPlants();
  }

  public static ArrayList<ZombieDna> getCurrentUserZombies() {
    return Account.current.getZombies();
  }

  public Store getStore() {
    return store;
  }

  public ArrayList<PlantDna> getPlants() {
    ArrayList<PlantDna> res = new ArrayList<>();
    for (PlantDna dna : PlantDna.getAllDnas())
      if (store.haveCard(dna))
        res.add(dna);
    return res;
  }

  public ArrayList<ZombieDna> getZombies() {
    ArrayList<ZombieDna> res = new ArrayList<>();
    for (ZombieDna dna : ZombieDna.getAllDnas())
      if (store.haveCard(dna))
        res.add(dna);
    return res;
  }

  private Effect<Unit> rename(String string) {
    if (getByUsername(string) != null) return Effect.error("invalid username");
    ALL.remove(username);
    username = string;
    ALL.put(username, this);
    return Effect.ok();
  }

  protected void delete() {
    ALL.remove(username);
  }

  private boolean matchPassword(String password) {
    return this.passwordHash.equals(password);
  }

  public static class LeaderBoardPage implements Page<Void> {
    String format = "    %-30s %5s %7s %5s %7s";
    String header = String.format(
            format, "Name", "|", "Score", "|", "Money"
    );
    String between = "----------------------------------------------------------------------";

    Text txt(String s) {
      Text txt = new Text(s);
      txt.setFill(Color.WHITESMOKE);
      txt.setFont(Font.font("monospaced", FontWeight.SEMI_BOLD, Program.screenX / 60));
      return txt;
    }

    @Override
    public Effect<Void> action() {
      return new Effect<>(h -> {
        VBox pane = new VBox();
        pane.setAlignment(Pos.TOP_CENTER);
        CloseButton closeButton = new CloseButton();
        closeButton.setOnMouseClicked(e -> {
          h.failure(new Error("end menu"));
        });
        HBox hBox = new HBox();
        hBox.getChildren().add(closeButton);
        hBox.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(hBox);

        pane.getChildren().add(txt(header));
        pane.getChildren().add(txt(between));
        ALL.values().stream().sorted((y, x) -> {
          if (x.score != y.score) return x.score - y.score;
          if (x.store.money != y.store.money) return x.store.money - y.store.money;
          return 0;
        }).forEachOrdered(x -> pane.getChildren().add(txt(String.format(
                format, x.username, "|", "" + x.score, "|", "" + x.store.money
        ))));
        ScrollPane scrollPane = new ScrollPane();
        pane.setBackground(new Background(new BackgroundImage(new Image(Program.getRes("images/wallpaper.jpg")), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        scrollPane.setContent(pane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setBackground(new Background(new BackgroundImage(new Image(Program.getRes("images/wallpaper.jpg")), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Program.stage.getScene().setRoot(scrollPane);
      });
    }

  }
}