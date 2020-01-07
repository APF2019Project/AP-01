package main;

import account.Account;
import account.ShopPage;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
import game.GameEngine;
import game.GameResult;
import page.Collection;
import page.Form;
import page.Message;
import page.Page;
import page.menu.ActionButton;
import page.menu.LinkButton;
import page.menu.Menu;
import page.menu.SimpleButton;
import util.Effect;
import util.Unit;

import javax.security.sasl.AuthenticationException;

public class Pages {
  public static final Page<GameResult> chooseGameType = new Menu<GameResult>(
      new SimpleButton<>("Day",
          new Collection<PlantDna>(Account::getCurrentUserPlants, 7).action()
              .map(GameEngine::newDayGame)),
      new SimpleButton<>("Water",
          new Collection<PlantDna>(Account::getCurrentUserPlants, 7)
              .action().map(GameEngine::newWaterGame)),
      new SimpleButton<>("Rail", Effect.noOp.map(x->null)),
      new SimpleButton<>("Zombie",
          new Collection<ZombieDna>(Account::getCurrentUserZombies, 7).action()
              .map(GameEngine::newZombieGame)),
      new SimpleButton<GameResult>("PVP", new Form("Enter opponent username").action()
          .map(opUsername -> Account.getByUsername(opUsername[0]))
          .flatMap(opUser -> new Collection<PlantDna>(Account::getCurrentUserPlants, 7).action()
              .flatMap(plantHand -> new Collection<ZombieDna>(opUser::getZombies, 7).action()
                  .map(zombieHand -> GameEngine.newPVPGame(plantHand, zombieHand))))));
  
  public static final Menu<Unit> mainMenu = new Menu<Unit>(
    new ActionButton<Unit>("play", chooseGameType.action().discardData()), 
    new LinkButton<Unit>("profile", Account.profilePage()),
    new LinkButton<Unit>("shop", new ShopPage()));
  public static final Menu<Void> loginMenu = new Menu<Void>(new ActionButton<Void>("create account",
    (new Form("Enter new username", "Enter password"))
    .action()
    .flatMap(data -> Account.create(data[0], data[1]))
    .map((x) -> "Account created successfully").show().showError()
  ), new ActionButton<Void>("login",
    (new Form("Enter username", "Enter password"))
    .action()
    .flatMap(data -> Account.login(data[0], data[1]))
    .flatMap(x -> mainMenu.action())
    .catchThen(e -> {
      if (e instanceof AuthenticationException) {
        return Message.show("login failed");
      }
      return Effect.noOp;
    })
  ), new LinkButton<Void>("leaderboard", Account.leaderBoardPage()));

  public static <U> Page<U> notImplemented() {
    return new Page<U>() {
      @Override
      public Effect<U> action() {
        new Message("ishalla in future").action();
        return Effect.error("end");
      }
    };
  }
}