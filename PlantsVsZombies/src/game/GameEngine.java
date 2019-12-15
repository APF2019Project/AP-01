package game;

import creature.Location;
import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
import creature.being.zombie.Zombie;
import creature.being.zombie.ZombieDna;
import exception.InvalidGameMoveException;
import page.Message;

import java.util.ArrayList;
import java.util.TreeSet;

public class GameEngine {
    private static GameEngine currnetGameEngine;
    private GameState gameState = GameState.LOADING;
    private PlayGroundData DATABASE;
    private Integer turn = 0;

    public Integer getTurn() {
        return turn;
    }

    public static GameEngine getCurrnetGameEngine() {
        return currnetGameEngine;
    }

    public boolean lineNumberChecker(Integer lineNumber) {
        return lineNumber >= 0 && lineNumber < DATABASE.width;
    }

    public boolean positionChecker(Integer position) {
        return position >= 0 && position < DATABASE.length;
    }

    public static GameResult newDayGame(ArrayList<PlantDna> hand) {
        Message.show("The game will start soonly here.");
        return null;
    }

    public boolean locationChecker(Integer lineNumber, Integer position) {
        return lineNumberChecker(lineNumber) && positionChecker(position);
    }

    public Plant getPlant(Integer lineNumber, Integer position) {
        if (locationChecker(lineNumber, position)) return null;
        for (Plant plant : DATABASE.plantsPerLine.get(lineNumber))
            if (plant.getLocation().equals(new Location(lineNumber, position)))
                return plant;
        return null;
    }

    public void newPlant(PlantDna dna, Integer lineNumber, Integer position) throws InvalidGameMoveException {
        if (!locationChecker(lineNumber, position) || getPlant(lineNumber, position) != null)
            throw new InvalidGameMoveException("can't plant here!");
        Plant plant = new Plant(dna, lineNumber, position);
        DATABASE.plantsPerLine.get(lineNumber).add(plant);
        DATABASE.plants.add(plant);
    }

    public void newZombie(ZombieDna dna, Integer lineNumber) throws InvalidGameMoveException {
        if (!lineNumberChecker(lineNumber)) throw new InvalidGameMoveException("can't insert zombie here");
        Zombie zombie = new Zombie(dna, lineNumber);
        DATABASE.zombiesPerLine.get(lineNumber).add(zombie);
        DATABASE.zombies.add(zombie);
    }

    public void killPlant(Plant plant) {
        DATABASE.plants.remove(plant);
        DATABASE.plantsPerLine.get(plant.getLocation().lineNumber).remove(plant);
    }

    public void killZombie(Zombie zombie) {
        DATABASE.zombies.remove(zombie);
        DATABASE.zombiesPerLine.get(zombie.getLocation().lineNumber).remove(zombie);
    }

    public TreeSet<Plant> getPlants() {
        return DATABASE.plants;
    }

    public TreeSet<Plant> getPlants(Integer lineNumber) {
        if (lineNumberChecker(lineNumber)) return DATABASE.plantsPerLine.get(lineNumber);
        return null;
    }

    public TreeSet<Zombie> getZombies() {
        return DATABASE.zombies;
    }

    public TreeSet<Zombie> getZombies(Integer lineNumber) {
        if (lineNumberChecker(lineNumber)) return DATABASE.zombiesPerLine.get(lineNumber);
        return null;
    }
}
