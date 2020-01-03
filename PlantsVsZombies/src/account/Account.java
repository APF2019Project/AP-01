package account;

import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Account implements Serializable {
  private static final long serialVersionUID = -5582985394951882515L;
  private static final String BACKUP_ADDRESS = Program.getBackupPath("account.ser");
  static Account current;
  private static Map<String, Account> ALL = new HashMap<>();
  Store store;
  private String username;
  private String passwordHash;
  //private String passwordSalt;
  public int score;

  public static Account getCurrentAccount() {
    return current;
  }

  public Store getStore() {
    return store;
  }

  private Account(String username, String password) {
    this.username = username;
    this.passwordHash = password;
    this.store = new Store(this);
    ALL.put(username, this);
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

  public static Page<Void> leaderBoardPage() {
    String format = "    %-30s %5s %7s %5s %7s\n";
    String header = String.format(
            format, "Name", "|", "Score", "|", "Money"
    ) +
            "----------------------------------------------------------------------\n";
    return () -> {
      String table = ALL.values().stream().sorted((y,x) -> {
        if (x.score != y.score) return x.score - y.score;
        if (x.store.money != y.store.money) return x.store.money - y.store.money;
        return 0;
      })
        .map(x -> String.format(
          format, x.username, "|", "" + x.score, "|", "" + x.store.money
        ))
        .collect(Collectors.joining());
      new Message(header + table).action();
      return Effect.error("end page");
    };
  }

  public static Page<Unit> profilePage() {
    return new Menu<>(
            new Button<>() {
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
            new ActionButton<>("change", () -> {
              (new Form("Enter username", "Enter password"))
                      .action()
                      .flatMap(data -> Account.login(data[0], data[1]))
                      .showError()
                      .map(x -> "Account changed successfully")
                      .show();
            }),
            new ActionButton<>("rename", () -> {
              (new Form("Enter new username"))
                      .action()
                      .flatMap(data -> Account.current.rename(data[0]))
                      .showError()
                      .map(x -> "Your username changed successfully")
                      .show();
            }),
            new ActionButton<>("create", () -> {
              (new Form("Enter new username", "Enter password"))
                      .action()
                      .flatMap(data -> Account.create(data[0], data[1]))
                      .map((x) -> "Account created successfully")
                      .show()
                      .showError();
            }),
            new ActionButton<>("Show", () -> new Message(current.username).action())
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

  public static Effect<Account> getByUsername(String username) {
    Account user = ALL.get(username);
    if (user == null) return Effect.error("Account not found");
    return Effect.ok(user);
  }

  public static Effect<Unit> create(String username, String password) {
    if (!getByUsername(username).isError()) 
      return Effect.error("invalid username");
    new Account(username, password);
    return Effect.ok();
  }

  public static Effect<Unit> login(String username, String password) {
    Effect<Unit> error = Effect.error("invalid username or password");
    return getByUsername(username).flatMap(user -> {
      if (!user.matchPassword(password)) return error;
      current = user;
      return Effect.ok();
    });
  }

  public static List<PlantDna> getCurrentUserPlants() {
    return Account.current.getPlants();
  }

  public static List<ZombieDna> getCurrentUserZombies() {
    return Account.current.getZombies();
  }

  public List<PlantDna> getPlants() {
    ArrayList<PlantDna> res = new ArrayList<>();
    for (PlantDna dna : PlantDna.getAllDnas())
      if (store.haveCard(dna))
        res.add(dna);
    return res;
  }

  public List<ZombieDna> getZombies() {
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
}