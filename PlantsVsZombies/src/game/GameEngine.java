package game;

import creature.Location;
import creature.ammunition.Ammunition;
import creature.ammunition.AmmunitionDna;
import creature.being.plant.Plant;
import creature.being.plant.PlantDna;
import creature.being.zombie.Zombie;
import creature.being.zombie.ZombieDna;
import exception.EndGameException;
import exception.InvalidGameMoveException;
import line.LawnMower;
import line.Line;
import line.LineState;
import main.ConsoleColors;
import page.Message;
import player.plant_player.DayModeUser;
import player.plant_player.PlantPlayer;
import player.plant_player.RailModeUser;
import player.plant_player.ZombieModeAI;
import player.zombie_player.DayModeAI;
import player.zombie_player.RailModeAI;
import player.zombie_player.ZombieModeUser;
import player.zombie_player.ZombiePlayer;

import java.util.*;

public class GameEngine {

    private PlantPlayer plantPlayer;
    private ZombiePlayer zombiePlayer;
    private static GameEngine currentGameEngine;
    private PlayGroundData DATABASE;
    private Integer turn = 0;
    private Random random;

    private ArrayList<ArrayList<ZombieDna>> zombieQueue;

    public GameEngine() {
        currentGameEngine = this;
        random = new Random();
    }

    public static GameResult newWaterGame(List<PlantDna> hand) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            if (i < 2 || i > 3)
                lines.add(new Line(i, LineState.DRY, new LawnMower(i)));
            else
                lines.add(new Line(i, LineState.WATER, new LawnMower(i)));

        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(new DayModeUser(hand), new DayModeAI(), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(GameMode.WATER, e.getWinner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }

    public static GameResult newDayGame(List<PlantDna> hand) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            lines.add(new Line(i, LineState.DRY, new LawnMower(i)));
        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(new DayModeUser(hand), new DayModeAI(), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(GameMode.DAY, e.getWinner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }

    public static GameResult newRailGame() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            lines.add(new Line(i, LineState.DRY, new LawnMower(i)));
        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(new RailModeUser(), new RailModeAI(), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(GameMode.PVP, e.getWinner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }

    public static GameResult newZombieGame(List<ZombieDna> hand) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            lines.add(new Line(i, LineState.DRY, new LawnMower(i)));
        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(new ZombieModeAI(), new ZombieModeUser(hand), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(GameMode.ZOMBIE, e.getWinner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }

    public static GameResult newPVPGame(List<PlantDna> plantHand, List<ZombieDna> zombieHand) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            lines.add(new Line(i, LineState.DRY, new LawnMower(i)));
        new GameEngine();
        GameEngine.getCurrentGameEngine().config(new GameDna(new DayModeUser(plantHand), new ZombieModeUser(zombieHand), lines));
        try {
            while (true) {
                getCurrentGameEngine().nextTurn();
            }
        } catch (EndGameException e) {
            return new GameResult(GameMode.PVP, e.getWinner(), getCurrentGameEngine().plantsKilled(), getCurrentGameEngine().zombiesKilled());
        }
    }

    public void internalError(String s) {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("internal error, probably you shoud just ignore it =) :");
        System.out.println(s);
        System.out.println("----------------------------------------------------------------------------");
    }

    public Random getRandom() {
        return random;
    }

    public Integer getTurn() {
        return turn;
    }

    private void config(GameDna gameDna) {
        plantPlayer = gameDna.plantPlayer;
        zombiePlayer = gameDna.zombiePlayer;
        DATABASE = new PlayGroundData(19, gameDna.lines);
        zombieQueue = new ArrayList<>();
        for (int i = 0; i < DATABASE.width; i++)
            zombieQueue.add(new ArrayList<>());
    }

    private boolean lineNumberChecker(Integer lineNumber) {
        return lineNumber >= 0 && lineNumber < DATABASE.width;
    }

    private boolean positionChecker(Integer position) {
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
        return !lineNumberChecker(lineNumber) || !positionChecker(position);
    }

    public Plant getPlant(Integer lineNumber, Integer position) {
        if (locationChecker(lineNumber, position)) return null;
        for (Plant plant : DATABASE.plantsPerLine.get(lineNumber))
            if (plant.getLocation().equals(new Location(lineNumber, position)))
                return plant;
        return null;
    }

    public Plant getPlant(Location location) {
        return getPlant(location.lineNumber, location.position);
    }

    public List<Zombie> getZombies(Integer lineNumber, Integer position) {
        if (locationChecker(lineNumber, position)) return null;
        SortedSet<Zombie> zombies = getZombies(lineNumber);
        ArrayList<Zombie> answer = new ArrayList<>();
        for (Zombie zombie : zombies)
            if (zombie.getLocation().equals(new Location(lineNumber, position)))
                answer.add(zombie);
        return answer;
    }

    public List<Zombie> getZombies(Location location) {
        return getZombies(location.lineNumber, location.position);
    }

    public void newPlant(PlantDna dna, Integer lineNumber, Integer position) throws InvalidGameMoveException {
        if (locationChecker(lineNumber, position) || getPlant(lineNumber, position) != null)
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

    public void newAmmunition(Location location, AmmunitionDna ammunitionDna, Plant plant) {
        Ammunition ammunition = new Ammunition(location, ammunitionDna, plant);
        DATABASE.ammunitionPerLine.get(location.lineNumber).add(ammunition);
        DATABASE.ammunition.add(ammunition);
    }


    public void addZombie(Zombie zombie) {
        DATABASE.zombiesPerLine.get(zombie.getLocation().lineNumber).add(zombie);
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

    public SortedSet<Plant> getDeadPlantsLastTurn() {
        return DATABASE.deadPlants;
    }

    public SortedSet<Zombie> getDeadZombiesLastTurn() {
        return DATABASE.deadZombies;
    }

    public void addSun(int sunAmount) {
        plantPlayer.addSun(sunAmount);
    }


    public void putZombie(ZombieDna dna, Integer lineNumber) throws InvalidGameMoveException {
        if (!lineNumberChecker(lineNumber) || zombieQueue.get(lineNumber).size() >= 2)
            throw new InvalidGameMoveException("can't insert zombie here");
        zombieQueue.get(lineNumber).add(dna);
    }

    public List<ArrayList<ZombieDna>> getZombieQueue() {
        return zombieQueue;
    }

    public void startZombieQueue() {
        // TODO: poshte ham naran !
        for (int i = 0; i < DATABASE.width; i++)
            for (ZombieDna dna : zombieQueue.get(i))
                try {
                    newZombie(dna, i);
                } catch (Exception ignored) {
                }

        zombieQueue = new ArrayList<>();
        for (int i = 0; i < DATABASE.width; i++)
            zombieQueue.add(new ArrayList<>());
    }

    public void nextTurn() throws EndGameException {
        DATABASE.deadPlantsLastTurn = DATABASE.deadPlants;
        DATABASE.deadZombiesLastTurn = DATABASE.deadZombies;
        DATABASE.deadPlants = new TreeSet<>();
        DATABASE.deadZombies = new TreeSet<>();
        plantPlayer.nextTurn();
        zombiePlayer.nextTurn();


        TreeSet<Plant> local2 = new TreeSet<>(DATABASE.plants);
        for (Plant plant : local2)
            if (DATABASE.plants.contains(plant))
                plant.nextTurn();

                
        TreeSet<Ammunition> local = new TreeSet<>(DATABASE.ammunition);
        for (Ammunition ammunition : local)
            if (DATABASE.ammunition.contains(ammunition))
                ammunition.nextTurn();

        
        TreeSet<Zombie> local3 = new TreeSet<>(DATABASE.zombies);
        for (Zombie zombie : local3)
            if (DATABASE.zombies.contains(zombie))
                zombie.nextTurn();

        turn += 1;
    }

    public void killAmmunition(Ammunition ammunition) {
        DATABASE.ammunition.remove(ammunition);
        DATABASE.ammunitionPerLine.get(ammunition.getLocation().lineNumber).remove(ammunition);
    }

    public void prettyPrint() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getLength(); j++) {
                Plant plant = getPlant(i, j);
                if (plant == null) res.append(".");
                else {
                    res.append(ConsoleColors.green(
                            "" + plant.getPlantDna().getName().charAt(0)
                    ));
                }
                int cnt = 0;
                for (Ammunition ammunition : DATABASE.ammunitionPerLine.get(i)) {
                    if (ammunition.getLocation().position == j) cnt++;
                }
                if (cnt == 0) res.append(".");
                else res.append(ConsoleColors.yellow("" + cnt));
                cnt = 0;
                char nm = 'A';
                for (Zombie zombie : getZombies(i)) {
                    if (zombie.getLocation().position == j) {
                        cnt++;
                        nm = zombie.getZombieDna().getName().charAt(0);
                    }
                }
                if (cnt == 0) res.append(".");
                else if (cnt == 1) res.append(ConsoleColors.red("" + nm));
                else res.append(ConsoleColors.red("" + cnt));
                res.append(" ");
            }
            res.append("\n");
        }
        Message.show(res.toString());
    }

    public List<Ammunition> getAmmunition(Integer lineNumber, Integer position) {
        if (locationChecker(lineNumber, position)) return new ArrayList<>();
        SortedSet<Ammunition> ammunitions = DATABASE.ammunitionPerLine.get(lineNumber);
        ArrayList<Ammunition> answer = new ArrayList<>();
        for (Ammunition ammunition : ammunitions)
            if (ammunition.getLocation().equals(new Location(lineNumber, position)))
                answer.add(ammunition);
        return answer;
    }

    public List<Ammunition> getAmmunition(Location location) {
        return getAmmunition(location.lineNumber, location.position);
    }

    public void activateLawnMower(Integer lineNumber) throws InvalidGameMoveException {
        if (!lineNumberChecker(lineNumber))
            throw new InvalidGameMoveException("invalid line number for activating lawn mower!");
        DATABASE.lines.get(lineNumber).activateLawnMower();
    }
}
