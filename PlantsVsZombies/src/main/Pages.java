package main;

import java.util.ArrayList;

import account.Account;
import account.ShopPage;
import page.menu.ActionButton;
import page.Collection;
import page.Form;
import page.menu.LinkButton;
import page.menu.Menu;
import page.Message;
import page.Page;
import util.Result;
import util.Unit;
import creature.being.plant.*;
import game.GameEngine;

public class Pages {
  public static final Menu<Unit> chooseGameType = new Menu<Unit>(
    new ActionButton<>("Day", () -> {
      
      new Collection<PlantDna>((ArrayList<PlantDna>)PlantDna.getAllDnas(), 2).action()
      .consume(hand -> {
        GameEngine.newDayGame(hand);
      });
    }),
    new LinkButton<>("Water", notImplemented()),
    new LinkButton<>("Rail", notImplemented()),
    new LinkButton<>("Zombie", notImplemented()),
    new LinkButton<>("PvP", notImplemented())
  );

  public static final Menu<Unit> mainMenu = new Menu<Unit>(
    new LinkButton<Unit>("play", chooseGameType),
    new LinkButton<Unit>("profile", Account.profilePage()),
    new LinkButton<Unit>("shop", new ShopPage())
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
    new ActionButton<Void>("create account", ()->{
      (new Form("Enter new username", "Enter password"))
      .action()
      .flatMap(data -> Account.create(data[0], data[1]))
      .map((x) -> "Account created successfully")
      .show()
      .showError();
    }),
    new ActionButton<Void>("login", ()->{
      (new Form("Enter username", "Enter password"))
      .action()
      .flatMap(data -> Account.login(data[0], data[1]))
      .showError()
      .flatMap(x -> mainMenu.action());
    }),
    new LinkButton<Void>("leaderboard", Account.leaderBoardPage())
  );
}