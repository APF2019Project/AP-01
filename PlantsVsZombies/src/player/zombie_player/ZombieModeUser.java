package player.zombie_player;

import creature.being.zombie.ZombieDna;
import exception.InvalidGameMoveException;
import game.GameEngine;
import page.Form;
import page.Message;
import page.menu.ActionButton;
import page.menu.ExitButton;
import page.menu.Menu;
import util.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieModeUser implements ZombiePlayer {

    private GameEngine gameEngine;
    private Integer coin = 50;
    private Random rnd;
    private List<ZombieDna> zombieDnas;

    public ZombieModeUser(List<ZombieDna> zombieDnas) {
        gameEngine = GameEngine.getCurrentGameEngine();
        rnd = gameEngine.getRandom();
        this.zombieDnas = zombieDnas;
    }

    @Override
    public void nextTurn() {
        new Menu<>(
                new ActionButton<>("Show hand", this::showHand),
                new ActionButton<>("Show lawn", this::showLawn),
                new ActionButton<>("Start", this::start),
                new ActionButton<>("Put", this::put),
                new ActionButton<>("Show lanes", this::showLanes),
                new ExitButton("End Turn")
        ).action();
    }

    private void put() {
        Result<String[]> result = new Form("Enter name", "Enter X").action();
        if (result == null || result.getValue().length == 0) {
            Message.show("Enter the name :/!");
            return;
        }
        for (ZombieDna zombieDna : zombieDnas)
            if (zombieDna.getName().equals(result.getValue()[0])) {
                if (zombieDna.getFirstHealth() * 10 > coin) {
                    Message.show("Cant select this one, not enough coin!");
                    return;
                }
                try {
                    gameEngine.putZombie(zombieDna, Integer.valueOf(result.getValue()[1]));
                    coin -= zombieDna.getFirstHealth() * 10;
                } catch (InvalidGameMoveException e) {
                    Message.show(e.getMessage());
                } catch (NumberFormatException e) {
                    Message.show("invalid input format :D!");
                }
                return;
            }
        Message.show("invalid name !");
    }

    private void start() {
        gameEngine.startZombieQueue();
    }

    private void showLanes() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ArrayList<ZombieDna>> zombieQueue = gameEngine.getZombieQueue();
        for (int i = 0; i < gameEngine.getWidth(); i++) {
            stringBuilder.append("line number ").append(i).append(":\n");
            for (ZombieDna dna : zombieQueue.get(i))
                stringBuilder.append(dna.toString()).append("\n");
            stringBuilder.append("------------------------------------------------------------------------------");
        }
        Message.show(stringBuilder.toString());
    }

    private void showLawn() {
        StringBuilder stringBuilder = new StringBuilder();
        gameEngine.getZombies().forEach(zombie -> stringBuilder.append(zombie.toString()).append('\n'));
        gameEngine.getPlants().forEach(plant -> stringBuilder.append(plant.toString()).append('\n'));
        Message.show(stringBuilder.toString());
    }

    public void showHand() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Coin: ").append(coin).append("\n");
        for (ZombieDna zombieDna : zombieDnas) {
            stringBuilder.append(zombieDna.toString()).append('\n');
        }
        Message.show(stringBuilder.toString());
    }
}

