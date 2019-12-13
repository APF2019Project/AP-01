package main;

import account.Account;
import page.ActionButton;
import page.Form;
import page.LinkButton;
import page.Menu;
import page.Message;
import page.Page;
import util.Result;
import util.Unit;

public class Pages {
  public static final Menu<Void> chooseGameType = new Menu<>(
    new LinkButton<>("Day", notImplemented()),
    new LinkButton<>("Water", notImplemented()),
    new LinkButton<>("Rail", notImplemented()),
    new LinkButton<>("Zombie", notImplemented()),
    new LinkButton<>("PvP", notImplemented())
  );

  public static final Menu<Void> mainMenu = new Menu<>(
    new LinkButton<>("play", chooseGameType),
    new LinkButton<>("profile", notImplemented()),
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