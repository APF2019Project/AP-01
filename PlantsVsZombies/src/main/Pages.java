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
import util.Result;
import util.Unit;

import java.util.ArrayList;
import java.util.function.Supplier;

class DataButton<U> implements Button<U> {

    private final String modeName;
    private final Supplier<Result<U>> gameSupplier;

    @Override
    public String getLabel() {
        return modeName;
    }

    @Override
    public String getHelp() {
        return "This button will create a game of type "+modeName;
    }

    @Override
    public Result<U> action() {
        return gameSupplier.get();
    }

    public DataButton(String modeName, Supplier<Result<U>> gameSupplier) {
        this.modeName = modeName;
        this.gameSupplier = gameSupplier;
    }
    
}

public class Pages {
    public static final Menu<GameResult> chooseGameType = new Menu<>(
            new DataButton<>("Day", () -> new Collection<>(
                    (ArrayList<PlantDna>) Account.getCurrentUserPlants(), 2
            ).action()
                    .map(GameEngine::newDayGame)),
            new DataButton<>("Water", () -> new Collection<>(
                    (ArrayList<PlantDna>) Account.getCurrentUserPlants(), 2
            ).action()
                    .map(GameEngine::newWaterGame)),
            new DataButton<>("Rail", Result.liftSupplier(GameEngine::newRailGame)),
            new DataButton<>("Zombie", () -> new Collection<>((ArrayList<ZombieDna>) Account.getCurrentUserZombies(), 2).action()
                    .map(GameEngine::newZombieGame)),
            new DataButton<>("PVP", () -> new Form("Enter opponent username").action()
                    .flatMap(opUsername -> Account.getByUsername(opUsername[0]))
                    .flatMap(opUser ->
                            new Collection<>((ArrayList<PlantDna>) Account.getCurrentUserPlants(), 2)
                                    .action()
                                    .flatMap(plantHand ->
                                            new Collection<>((ArrayList<ZombieDna>) opUser.getZombies(), 2)
                                                    .action().map(zombieHand -> GameEngine.newPVPGame(plantHand, zombieHand))
                                    )
                    ))
    );

    public static final Menu<Unit> mainMenu = new Menu<>(
            new ActionButton<>("play", () -> {
                Result<GameResult> x = chooseGameType.action();
                if (!x.isError()) {
                    Message.show("Your game finished with:\n"+x.getValue());
                }
            }),
            new LinkButton<>("profile", Account.profilePage()),
            new LinkButton<>("shop", new ShopPage())
    );
    public static final Menu<Void> loginMenu = new Menu<>(
            new ActionButton<>("create account", () -> {
                (new Form("Enter new username", "Enter password"))
                        .action()
                        .flatMap(data -> Account.create(data[0], data[1]))
                        .map(x -> "Account created successfully")
                        .show()
                        .showError();
            }),
            new ActionButton<>("login", () -> {
                (new Form("Enter username", "Enter password"))
                        .action()
                        .flatMap(data -> Account.login(data[0], data[1]))
                        .showError()
                        .flatMap(x -> mainMenu.action());
            }),
            new LinkButton<>("leaderboard", Account.leaderBoardPage())
    );

    public static <U> Page<U> notImplemented() {
        return () -> {
            new Message("ishalla in future").action();
            return Result.error("end");
        };
    }
}