package game;

import exception.InvalidGameMoveException;

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
        return lineNumber >= 0 && lineNumber < DATABASE.weidth;
    }

    public boolean positionChecker(Integer position) {
        return position >= 0 && position < DATABASE.length;
    }

    public boolean locationChecker(Integer lineNumber, Integer position) {
        return lineNumberChecker(lineNumber) && positionChecker(position);
    }

    public Plant getPlant(Integer lineNumber, Integer position) {
        if (locationChecker(lineNumber, position)) return null;
        for (Plant plant : DATABASE.plantsPerLine[lineNumber])
            // TODO: check location of plant;
            return plant;

        return null;
    }

    public void newPlant(PlantDNA dna, Integer lineNumber, Integer position) throws InvalidGameMoveException {
        if (!locationChecker(lineNumber, position) || getPlant(lineNumber, position) != null)
            throw new InvalidGameMoveException("can't plant here!");
        Plant plant = new PlayGroundData(dns, lineNumber, position);
        DATABASE.plantsPerLine[lineNumber].add(plant);
        DATABASE.plants.add(plant);
    }

    public void newZombie(ZombieDNA dna, Integer lineNumber) throws InvalidGameMoveException {
        if (!lineNumberChecker(lineNumber)) throw new InvalidGameMoveException("can't insert zombie here");
        Zombie zombie = new Zombie(dna, lineNumber);
        DATABASE.zombiesPerLine[lineNumber].add(zombie);
        DATABASE.zombies.add(zombie);
    }

    public void killPlant(Plant plant) {
        DATABASE.plants.remove(plant);
        DATABASE.plantsPerLine.remove(plant.getLocation().lineNumber);
    }

    public void killZombie(Zombie zombie) {
        DATABASE.zombies.remove(zombie);
        DATABASE.zombiesPerLine.remove(zombie.getLocation().lineNumber);
    }

    public TreeSet<Plant> getPlants() {
        return DATABASE.plants;
    }

    public TreeSet<Plant> getPlants(Integer lineNumber) {
        if (lineNumberChecker(lineNumber)) return DATABASE.plantsPerLine[lineNumber];
        return null;
    }

    public TreeSet<Zombie> getZombies() {
        return DATABASE.zombies;
    }

    public TreeSet<Zombie> getZombies(Integer lineNumber) {
        if (lineNumberChecker(lineNumber)) return DATABASE.zombiesPerLine[lineNumber];
        return null;
    }
}
