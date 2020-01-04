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
import page.menu.Button;
import page.menu.LinkButton;
import page.menu.Menu;
import util.Effect;
import util.Unit;

import java.util.ArrayList;
import java.util.function.Supplier;

import javax.security.sasl.AuthenticationException;

class DataButton<U> implements Button<U> {

  private final String modeName;
  private final Supplier<Effect<U>> gameSupplier;

  @Override
  public String getLabel() {
    return modeName;
  }

  @Override
  public String getHelp() {
    return "This button will create a game of type " + modeName;
  }

  @Override
  public Effect<U> action() {
    return gameSupplier.get();
  }

  public DataButton(String modeName, Supplier<Effect<U>> gameSupplier) {
    this.modeName = modeName;
    this.gameSupplier = gameSupplier;
  }

}

public class Pages {
  public static final Page<GameResult> chooseGameType = new Page<GameResult>() {
    @Override
    public Effect<GameResult> action() {
      // TODO Auto-generated method stub
      return Effect.ok(null);
    }

  };/*new Menu<GameResult>(
      new DataButton<>("Day",
          () -> new Collection<PlantDna>((ArrayList<PlantDna>) Account.getCurrentUserPlants(), 7).action()
              .map(GameEngine::newDayGame)),
      new DataButton<>("Water",
          () -> new Collection<PlantDna>((ArrayList<PlantDna>) Account.getCurrentUserPlants(), 7)
              .action().map(GameEngine::newWaterGame)),
      new DataButton<>("Rail", Effect.liftSupplier(GameEngine::newRailGame)),
      new DataButton<>("Zombie",
          () -> new Collection<ZombieDna>((ArrayList<ZombieDna>) Account.getCurrentUserZombies(), 7).action()
              .map(GameEngine::newZombieGame)),
      new DataButton<GameResult>("PVP", () -> new Form("Enter opponent username").action()
          .flatMap(opUsername -> Account.getByUsername(opUsername[0]))
          .flatMap(opUser -> new Collection<PlantDna>((ArrayList<PlantDna>) Account.getCurrentUserPlants(), 7).action()
              .flatMap(plantHand -> new Collection<ZombieDna>((ArrayList<ZombieDna>) opUser.getZombies(), 7).action()
                  .map(zombieHand -> GameEngine.newPVPGame(plantHand, zombieHand))))));
*/
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