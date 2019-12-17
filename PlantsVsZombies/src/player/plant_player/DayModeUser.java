package player.plant_player;

import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
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

public class DayModeUser implements PlantPlayer {

    private GameEngine gameEngine;
    private Integer sun = 2;
    private Random rnd;
    private List<PlantDna> plantDans;
    private List<Integer> coolDownTimeLeft;
    private Integer selected;

    public DayModeUser(List<PlantDna> plantDans) {
        gameEngine = GameEngine.getCurrentGameEngine();
        rnd = gameEngine.getRandom();
        this.plantDans = plantDans;
        coolDownTimeLeft = new ArrayList<>();
        for (int i = 0; i < plantDans.size(); i++)
            coolDownTimeLeft.add(0);
    }

    private void randomSunAdder() {
        Integer added = rnd.nextInt(2) * (2 + rnd.nextInt(4));
        sun += added;
    }

    @Override
    public void nextTurn() {
        selected = null;
        randomSunAdder();
        for (int i = 0; i < coolDownTimeLeft.size(); i++) {
            coolDownTimeLeft.set(i, coolDownTimeLeft.get(i) - 1);
            if (coolDownTimeLeft.get(i) < 0)
                coolDownTimeLeft.set(i, 0);
        }
        new Menu<>(
                new ActionButton<>("Show hand", this::showHand),
                new ActionButton<>("Show lawn", this::showLawn),
                new ActionButton<>("Plant", this::createPlant),
                new ActionButton<>("Remove", this::removePlant),
                new ActionButton<>("Select", this::select),
                new ExitButton("End Turn")
        ).action();
    }

    private void select() {
        Result<String[]> result = new Form("Enter name").action();
        if (result == null || result.getValue().length == 0) {
            Message.show("Enter the name :/!");
            return;
        }
        for (int i = 0; i < plantDans.size(); i++)
            if (plantDans.get(i).getName().equals(result.getValue()[0])) {
                if (coolDownTimeLeft.get(i) > 0) {
                    Message.show("Cant select this one, cool down time left !");
                    return;
                }
                selected = i;
                return;
            }
        Message.show("invalid name !");
    }

    private void removePlant() {
        Result<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            Integer x = Integer.valueOf(result.getValue()[0]);
            Integer y = Integer.valueOf(result.getValue()[1]);
            Plant plant = gameEngine.getPlant(x, y);
            if (plant == null) throw new InvalidGameMoveException("there is no plant in that location!");
            gameEngine.killPlant(plant);
        } catch (InvalidGameMoveException e) {
            Message.show(e.getMessage());
        }
    }

    private void createPlant() {
        Result<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            if (selected == null) throw new InvalidGameMoveException("Select a card first! =)");
            Integer x = Integer.valueOf(result.getValue()[0]);
            Integer y = Integer.valueOf(result.getValue()[1]);
            gameEngine.newPlant(plantDans.get(selected), x, y);
            coolDownTimeLeft.set(selected, plantDans.get(selected).getCoolDown());
        } catch (InvalidGameMoveException e) {
            Message.show(e.getMessage());
        }
        selected = null;
    }

    private void showLawn() {
        StringBuilder stringBuilder = new StringBuilder();
        gameEngine.getZombies().forEach(zombie -> stringBuilder.append(zombie.toString()).append('\n'));
        gameEngine.getPlants().forEach(plant -> stringBuilder.append(plant.toString()).append('\n'));
        Message.show(stringBuilder.toString());
    }

    @Override
    public void showHand() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < plantDans.size(); i++) {
            stringBuilder.append(plantDans.get(i).toString()).append('\n');
            stringBuilder.append("cool down time left: ").append(coolDownTimeLeft.get(i)).append('\n');
        }
        Message.show(stringBuilder.toString());
    }
}
