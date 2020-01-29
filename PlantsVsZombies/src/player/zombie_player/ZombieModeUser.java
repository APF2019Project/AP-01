package player.zombie_player;

import creature.being.plant.Plant;
import creature.being.zombie.ZombieDna;
import exception.EndGameException;
import exception.InvalidGameMoveException;
import exception.Winner;
import game.GameEngine;
import graphic.SimpleButton;
import page.Form;
import page.Message;
import page.menu.ActionButton;
import page.menu.ExitButton;
import page.menu.Menu;
import util.Effect;
import util.Unit;

import javafx.scene.Group;
import main.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieModeUser implements ZombiePlayer {

    private GameEngine gameEngine;
    private Integer coin = 50;
    private Random rnd;
    private List <ZombieDna> zombieDnas;
    private Group group;

    public ZombieModeUser(List<ZombieDna> zombieDnas) {
        gameEngine = GameEngine.getCurrentGameEngine();
        rnd = gameEngine.getRandom();
        this.zombieDnas = zombieDnas;
        this.group = gameEngine.getPlayerGroup();
        coin = 100;
        /*group.getChildren().add(new SimpleButton(
            Program.screenX*0.01,
            0,
            Program.screenX*0.58,
            Program.screenY*0.145,
            "Put zombie",
            Effect.noOp
        ));*/
    }

    @Override
    public void nextTurn() throws EndGameException {
        for (Plant plant : gameEngine.getDeadPlantsLastTurn())
            coin += plant.getPlantDna().getFirstHealth();
        boolean flag = false;
        for (ZombieDna zombieDna : zombieDnas)
            if (zombieDna.getFirstHealth() * 10 <= coin)
                flag = true;
        if (!flag && gameEngine.getZombies().isEmpty()) throw new EndGameException(Winner.PLANTS);
    }

    private void put() {
        /*Effect<String[]> result = new Form("Enter name", "Enter X").action();
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
        Message.show("invalid name !");*/
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

