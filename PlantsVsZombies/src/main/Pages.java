package main;

import java.util.ArrayList;

import account.Account;
import page.menu.ActionButton;
import page.Collection;
import page.Form;
import page.menu.LinkButton;
import page.menu.Menu;
import page.Message;
import page.Page;
import util.Result;
import creature.being.plant.*;
import game.GameEngine;

public class Pages {
  public static final Menu<Void> chooseGameType = new Menu<>(
    new ActionButton<>("Day", () -> {
      ArrayList<PlantDna> options = new ArrayList<>();
      options.add(new PlantDna("tofangi"));
      options.add(new PlantDna("bombi"));
      options.add(new PlantDna("khari"));
      options.add(new PlantDna("gol"));
      new Collection<PlantDna>(options, 2).action()
      .consume(hand -> {
        GameEngine.newDayGame(hand);
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