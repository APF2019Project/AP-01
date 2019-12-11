package main;

import account.Account;
import page.ActionButton;
import page.Form;
import page.LinkButton;
import page.Menu;
import page.Page;
import util.Result;

public class Pages {
  public static final Menu<Void> chooseGameType = new Menu<Void>(
    new LinkButton<Void>("Day", notImplemented()),
    new LinkButton<Void>("Water", notImplemented()),
    new LinkButton<Void>("Rail", notImplemented()),
    new LinkButton<Void>("Zombie", notImplemented()),
    new LinkButton<Void>("PvP", notImplemented())
  );

  public static final Menu<Void> mainMenu = new Menu<Void>(
    new LinkButton<Void>("play", chooseGameType),
    new LinkButton<Void>("profile", notImplemented()),
    new LinkButton<Void>("shop", notImplemented())
  );
  public static <U> Page<U> notImplemented(){
    return new Page<U>(){
      @Override
      public Result<U> action() {
        System.out.println("ishalla in future");
        return Result.error("end");
      }
    };
  }
  public static final Menu<Void> loginMenu = new Menu<Void>(
    new ActionButton("create account", ()->{
      (new Form("Enter new username:", "Enter password:"))
      .action()
      .flatMap(data -> Account.create(data[0], data[1]))
      .map((x) -> "Account created successfully")
      .print()
      .printError();
    }),
    new ActionButton("login", ()->{
      (new Form("Enter new username:", "Enter password:"))
      .action()
      .flatMap(data -> Account.login(data[0], data[1]))
      .printError()
      .flatMap(x -> mainMenu.action());
    }),
    new LinkButton<Void>("leaderboard", notImplemented())
  );
}