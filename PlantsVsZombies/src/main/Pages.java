package main;

import account.Account;
import page.ActionButton;
import page.Collection;
import page.Form;
import page.LinkButton;
import page.Menu;
import page.Message;
import page.Page;
import util.Result;
import creature.being.plant.*;
import game.GamePages;

public class Pages {
  public static final Menu<Void> chooseGameType = new Menu<>(
    new ActionButton<>("Day", () -> {
      new Collection<PlantDna>(new PlantDna[]{}).action()
      .flatMap(hand -> {
        Message.show("your hand is: ");
        return GamePages.dayPage.action();
      });
    }),
    new LinkButton<>("Water", notImplemented()),
    new LinkButton<>("Rail", notImplemented()),
    new LinkButton<>("Zombie", notImplemented()),
    new LinkButton<>("PvP", notImplemented())
  );

  public static final Menu<Void> mainMenu = new Menu<>(
    new LinkButton<>("play", chooseGameType),
    new LinkButton<>("profile", Account.profilePage()),
    new LinkButton<>("shop", notImplemented())
  );
  public static <U> Page<U> notImplemented(){
    return new Page<U>(){
      @Override
      public Result<U> action() {
        new Message("ishalla in future").action();
        return Result.error("end");
      }
    };
  }
  public static final Menu<Void> loginMenu = new Menu<Void>(
    new ActionButton("create account", ()->{
      (new Form("Enter new username", "Enter password"))
      .action()
      .flatMap(data -> Account.create(data[0], data[1]))
      .map((x) -> "Account created successfully")
      .show()
      .showError();
    }),
    new ActionButton("login", ()->{
      (new Form("Enter username", "Enter password"))
      .action()
      .flatMap(data -> Account.login(data[0], data[1]))
      .showError()
      .flatMap(x -> mainMenu.action());
    }),
    new LinkButton<Void>("leaderboard", Account.leaderBoardPage())
  );
}