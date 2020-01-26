package player.plant_player;

import account.Account;
import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
import exception.EndGameException;
import exception.InvalidGameMoveException;
import game.GameEngine;
import graphic.card.GameCard;
import javafx.scene.Group;
import main.Program;
import page.Form;
import page.Message;
import util.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RailModeUser implements PlantPlayer {

    private GameEngine gameEngine;
    private Random rnd;
    private List<PlantDna> plantDans;
    private Integer selected;
    private int remainTurn;
    private Group group;
    private ArrayList<GameCard> gameCards = new ArrayList<>();

    public RailModeUser() {
        gameEngine = GameEngine.getCurrentGameEngine();
        rnd = gameEngine.getRandom();
        this.plantDans = new ArrayList<>();
        this.group = gameEngine.getPlayerGroup();
    }

    private void randomPlantDnaAdder() {
        if (remainTurn != 0) {
            if (gameEngine.getTurn() % GameEngine.getFRAME() == 0) remainTurn--;
            return;
        }
        remainTurn = (1 + rnd.nextInt(3))*GameEngine.getFRAME();
        List<PlantDna> allDnas = Account.getCurrentUserPlants();
        PlantDna plantDna = allDnas.get(rnd.nextInt(allDnas.size()));
        plantDans.add(plantDna);
        GameCard gameCard = new GameCard(this, plantDna, 0, 10, Program.screenX * 0.06);
        gameCards.add(gameCard);
        group.getChildren().add(gameCard);
    }

    @Override
    public void nextTurn() throws EndGameException {
        randomPlantDnaAdder();
        reArrange();
    }

    private void reArrange() {
        int i = 0;
        for (GameCard gameCard: gameCards) {
            gameCard.setTranslateX((i + 0.3) * Program.screenX * 0.11);
            i++;
        }
    }

    private void select() {
        /*Effect<String[]> result = new Form("Enter name").action();
        if (result == null || result.getValue().length == 0) {
            Message.show("Enter the name :/!");
            return;
        }
        for (int i = 0; i < plantDans.size(); i++)
            if (plantDans.get(i).getName().equals(result.getValue()[0])) {
                selected = i;
                return;
            }
        Message.show("invalid name !");*/
    }

    private void showRecord() {
        Message.show("You killed " + gameEngine.getDeadZombies().size() + " zombies.");
    }

    private void removePlant() {
        Effect<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            Integer x = 0;//Integer.valueOf(result.getValue()[0]);
            Integer y = 0;//Integer.valueOf(result.getValue()[1]);
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
        Effect<String[]> result = new Form("Enter X", "Enter Y").action();
        try {
            if (selected == null) throw new InvalidGameMoveException("Select a card first! =)");
            Integer x = 0;//Integer.valueOf(result.getValue()[0]);
            Integer y = 0;//Integer.valueOf(result.getValue()[1]);
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
        
    }

    @Override
    public void addSun(int sunAmount) {

    }

    @Override
    public void plant(PlantDna dna, int x, int y) {
        try {
            gameEngine.newPlant2(dna, x, y);
            int i = plantDans.indexOf(dna);
            plantDans.remove(i);
            group.getChildren().remove(gameCards.get(i));
            gameCards.remove(i);
            reArrange();
        } catch (InvalidGameMoveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
