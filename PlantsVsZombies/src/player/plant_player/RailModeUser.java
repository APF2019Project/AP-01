package player.plant_player;

import account.Account;
import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
import exception.EndGameException;
import exception.InvalidGameMoveException;
import game.GameEngine;
import main.Program;
import page.Form;
import page.Message;
import page.menu.ActionButton;
import page.menu.ExitButton;
import page.menu.Menu;
import util.Result;
import util.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RailModeUser implements PlantPlayer {

    private GameEngine gameEngine;
    private Random rnd;
    private List<PlantDna> plantDans;
    private Integer selected;

    public RailModeUser() {
        gameEngine = GameEngine.getCurrentGameEngine();
        rnd = gameEngine.getRandom();
        this.plantDans = new ArrayList<>();
    }

    private void randomPlantDnaAdder() {
        if (rnd.nextInt(3) == 0 && plantDans.size() < 10) {
            List<PlantDna> allDnas = Account.getCurrentUserPlants();
            plantDans.add(allDnas.get(rnd.nextInt(allDnas.size())));
        }
    }

    @Override
    public void nextTurn() throws EndGameException {
        selected = null;
        randomPlantDnaAdder();
        Result<Unit> x = new Menu<>(
                gameEngine::pretty,
                new ActionButton<>("List", this::showHand),
                new ActionButton<>("show lawn pretty", GameEngine.getCurrentGameEngine()::prettyPrint),
                new ActionButton<>("Show lawn", this::showLawn),
                new ActionButton<>("Record", this::showRecord),
                new ActionButton<>("Plant", this::createPlant),
                new ActionButton<>("Remove", this::removePlant),
                new ActionButton<>("Select", this::select),
                new ExitButton("End Turn")
        ).action();
        if (x.isError()) throw new EndGameException();
    }

    private void select() {
        Result<String[]> result = new Form("Enter name").action();
        if (result == null || result.getValue().length == 0) {
            Message.show("Enter the name :/!");
            return;
        }
        for (int i = 0; i < plantDans.size(); i++)
            if (plantDans.get(i).getName().equals(result.getValue()[0])) {
                selected = i;
                return;
            }
        Message.show("invalid name !");
    }

    private void showRecord() {
        Message.show("You killed " + gameEngine.getDeadZombies().size() + " zombies.");
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
        } catch (NumberFormatException e) {
            Message.show("invalid input format :D!");
        }
    }

    private void createPlant() {
        Result<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            if (selected == null) throw new InvalidGameMoveException("Select a card first! =)");
            Integer x = Integer.valueOf(result.getValue()[0]);
            Integer y = Integer.valueOf(result.getValue()[1]);
            gameEngine.newPlant(plantDans.get(selected), x, y);
            plantDans.remove((int) selected);
        } catch (InvalidGameMoveException e) {
            Message.show(e.getMessage());
        } catch (NumberFormatException e) {
            Message.show("invalid input format :D!");
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
        Program.clearScreen();
        int cnt = 0;
        for (PlantDna plantDan : plantDans) {
            boolean isSelected = selected != null && cnt == selected;
            String id = isSelected ? "X" : cnt+"";
            System.out.println(id + ". " + plantDan.getName());
            cnt++;
        }
        System.out.println("Enter a number for select or press enter to continue...");
        String s = Program.scanner.nextLine();
        try {
            selected = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            
        }
    }

    @Override
    public void addSun(int sunAmount) {

    }
}
