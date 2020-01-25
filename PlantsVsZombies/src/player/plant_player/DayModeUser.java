package player.plant_player;

import creature.Location;
import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
import exception.EndGameException;
import exception.InvalidGameMoveException;
import game.GameEngine;
import graphic.card.GameCard;
import graphic.card.SimpleCard;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import main.Program;
import page.Form;
import page.Message;
import page.menu.ActionButton;
import page.menu.ExitButton;
import page.menu.Menu;
import util.Effect;
import util.Unit;

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
    private Group group;

    public DayModeUser(List<PlantDna> plantDans) {
        gameEngine = GameEngine.getCurrentGameEngine();
        rnd = gameEngine.getRandom();
        group = gameEngine.getPlayerGroup();
        this.plantDans = plantDans;
        coolDownTimeLeft = new ArrayList<>();
        int i = 0;
        for (PlantDna dna : plantDans) {
            coolDownTimeLeft.add(0);
            group.getChildren()
                    .add(new GameCard(this, dna, (i + 0.3) * Program.screenX * 0.11, 10, Program.screenX * 0.06));
            i++;
        }
    }

    private void randomSunAdder() {
        Integer added = rnd.nextInt(2) * (2 + rnd.nextInt(4));
        sun += added;
    }

    @Override
    public void nextTurn() throws EndGameException {
        selected = null;
        randomSunAdder();
        for (int i = 0; i < coolDownTimeLeft.size(); i++) {
            coolDownTimeLeft.set(i, coolDownTimeLeft.get(i) - 1);
            if (coolDownTimeLeft.get(i) < 0)
                coolDownTimeLeft.set(i, 0);
        }
    }

    private void selectNum(int i) {
        if (coolDownTimeLeft.get(i) > 0) {
            Message.show("Cant select this one, cool down time left !");
            return;
        }
        if (plantDans.get(i).getGamePrice() > sun) {
            Message.show("Cant select this one, not enough sun!");
            return;
        }
        selected = i;
    }

    private void select() {
        /*
         * Effect<String[]> result = new Form("Enter name").action(); if (result == null
         * || result.getValue().length == 0) { Message.show("Enter the name :/!");
         * return; } for (int i = 0; i < plantDans.size(); i++) if
         * (plantDans.get(i).getName().equals(result.getValue()[0])) { selectNum(i);
         * return; } Message.show("invalid name !");
         */
    }

    private void removePlant() {
        Effect<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            Integer x = 0;// Integer.valueOf(result.getValue()[0]);
            Integer y = 0;// Integer.valueOf(result.getValue()[1]);
            Plant plant = gameEngine.getPlant(x, y);
            if (plant == null)
                throw new InvalidGameMoveException("there is no plant in that location!");
            gameEngine.killPlant(plant);
        } catch (InvalidGameMoveException e) {
            Message.show(e.getMessage());
        } catch (NumberFormatException e) {
            Message.show("invalid input format :D!");
        }
    }

    private void createPlant() {
        Effect<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            if (selected == null)
                throw new InvalidGameMoveException("Select a card first! =)");
            Integer x = 0;// Integer.valueOf(result.getValue()[0]);
            Integer y = 0;// Integer.valueOf(result.getValue()[1]);
            PlantDna p = plantDans.get(selected);
            gameEngine.newPlant(p, x, y);
            sun -= p.getGamePrice();
            coolDownTimeLeft.set(selected, p.getCooldown());
            selected = null;
        } catch (InvalidGameMoveException e) {
            Message.show(e.getMessage());
        } catch (NumberFormatException e) {
            Message.show("invalid input format :D!");
        }
    }

    @Override
    public void showHand() {
        Program.clearScreen();
        System.out.println("Sun: " + sun + "\n");
        for (int i = 0; i < plantDans.size(); i++) {
            boolean isSelected = (selected != null && i == selected);
            String id = (isSelected ? "X" : i + "");
            System.out.println(id + ". " + plantDans.get(i).getName());
            System.out.println("cool down time left: " + coolDownTimeLeft.get(i));
        }
        System.out.println("Enter a number for select or press enter to continue...");
        String s = Program.scanner.nextLine();
        try {
            selectNum(Integer.parseInt(s));
        } catch (NumberFormatException e) {

        }
    }

    @Override
    public void addSun(int sunAmount) {
        sun += sunAmount;

    }

    @Override
    public void plant(PlantDna dna, int x, int y) {
        try {
            gameEngine.newPlant2(dna, x, y);
        } catch (InvalidGameMoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
