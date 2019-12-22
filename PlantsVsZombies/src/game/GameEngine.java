package game;

import creature.Location;
import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
import creature.being.zombie.Zombie;
import creature.being.zombie.ZombieDna;
import exception.EndGameException;
import exception.InvalidGameMoveException;
import line.Line;
import line.LineState;
import page.Message;
import player.plant_player.DayModeUser;
import player.plant_player.PlantPlayer;
import player.plant_player.RailModeUser;
import player.zombie_player.DayModeAI;
import player.zombie_player.RailModeAI;
import player.zombie_player.ZombiePlayer;

import java.util.*;

public class GameEngine {

    private PlantPlayer plantPlayer;
    private ZombiePlayer zombiePlayer;
    private static GameEngine currentGameEngine;
    private GameState gameState = GameState.LOADING;
    private PlayGroundData DATABASE;
    private Integer turn = 0;
    private Random random;

    private ArrayList<ArrayList<ZombieDna>> zombieQueue;

    public GameEngine() {
        currentGameEngine = this;
        random = new Random();
    }

    public static GameResult newDayGame(List<PlantDna> hand) {
        Message.show("The game will start soonly here.");
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            lines.add(new Line(i, LineState.DRY, null));
        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(GameMode.DAY, new DayModeUser(hand), new DayModeAI(), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(e.getWiner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }


    public static GameResult newRailGame() {
        Message.show("The game will start soonly here.");
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            lines.add(new Line(i, LineState.DRY, null));
        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(GameMode.RAIL, new RailModeUser(), new RailModeAI(), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(e.getWiner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }

    public Random getRandom() {
        return random;
    }

    public Integer getTurn() {
        return turn;
    }

    public void config(GameDna gameDna) {
        plantPlayer = gameDna.plantPlayer;
        zombiePlayer = gameDna.zombiePlayer;
        DATABASE = new PlayGroundData(19, gameDna.lines);
        zombieQueue = new ArrayList<>(DATABASE.width);
    }

    public boolean lineNumberChecker(Integer lineNumber) {
        return lineNumber >= 0 && lineNumber < DATABASE.width;
    }

    public boolean positionChecker(Integer position) {
        return position >= 0 && position < DATABASE.length;
    }

    public Integer getWidth() {
        return DATABASE.width;
    }

    public Integer getLength() {
        return DATABASE.length;
    }

    public static GameEngine getCurrentGameEngine() {
        return currentGameEngine;
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

    //TODO handling first position of zombie considering 0-base
    public void newZombie(ZombieDna dna, Integer lineNumber) throws InvalidGameMoveException {
        if (!lineNumberChecker(lineNumber)) throw new InvalidGameMoveException("can't insert zombie here");
        Zombie zombie = new Zombie(dna, lineNumber, getLength());
        DATABASE.zombiesPerLine.get(lineNumber).add(zombie);
        DATABASE.zombies.add(zombie);
    }

    public void killPlant(Plant plant) {
        DATABASE.plants.remove(plant);
        DATABASE.plantsPerLine.get(plant.getLocation().lineNumber).remove(plant);
        DATABASE.plantsKilled += 1;
        DATABASE.deadPlants.add(plant);
    }

    public void killZombie(Zombie zombie) {
        DATABASE.zombies.remove(zombie);
        DATABASE.zombiesPerLine.get(zombie.getLocation().lineNumber).remove(zombie);
        DATABASE.zombiesKilled += 1;
        DATABASE.deadZombies.add(zombie);
    }

    public SortedSet<Plant> getPlants() {
        return DATABASE.plants;
    }

    public SortedSet<Plant> getPlants(Integer lineNumber) {
        if (lineNumberChecker(lineNumber)) return DATABASE.plantsPerLine.get(lineNumber);
        return null;
    }

    public SortedSet<Zombie> getZombies() {
        return DATABASE.zombies;
    }

    public SortedSet<Zombie> getZombies(Integer lineNumber) {
        if (lineNumberChecker(lineNumber)) return DATABASE.zombiesPerLine.get(lineNumber);
        return null;
    }

    public Integer zombiesKilled() {
        return DATABASE.zombiesKilled;
    }

    public Integer plantsKilled() {
        return DATABASE.plantsKilled;
    }

    public SortedSet<Plant> getDeadPlnats() {
        return DATABASE.deadPlants;
    }

    public SortedSet<Zombie> getDeadZombies() {
        return DATABASE.deadZombies;
    }

    public void zombiePlayerShowHnad() {
        zombiePlayer.showHand();
    }

    public void PlantPlayerShowHand() {
        plantPlayer.showHand();
    }

    public void putZombie(ZombieDna dna, Integer lineNumber) throws InvalidGameMoveException {
        if (!lineNumberChecker(lineNumber) || zombieQueue.get(lineNumber).size() >= 2)
            throw new InvalidGameMoveException("can't insert zombie here");
        zombieQueue.get(lineNumber).add(dna);
    }

    public void startZombieQueue() {
        for (int i = 0; i < DATABASE.width; i++)
            for (ZombieDna dna : zombieQueue.get(i))
                try {
                    newZombie(dna, i);
                } catch (Exception ignored) {
                }

        zombieQueue = new ArrayList<>(DATABASE.width);

    }

    public void nextTurn() throws EndGameException {
        DATABASE.deadPlantsLastTurn = DATABASE.deadPlants;
        DATABASE.deadZombiesLastTurn = DATABASE.deadZombies;
        DATABASE.deadPlants = new TreeSet<>();
        DATABASE.deadZombies = new TreeSet<>();
        plantPlayer.nextTurn();
        zombiePlayer.nextTurn();

        // TODO: interacton between plants as zombies =)

        turn += 1;
    }

}
