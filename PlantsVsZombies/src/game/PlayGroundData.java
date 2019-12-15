package game;

import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import line.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PlayGroundData {

    final Integer width;
    final Integer length;

    SortedSet<Plant> plants = new TreeSet<>();
    SortedSet<Zombie> zombies = new TreeSet<>();
    Line[] lines;

    List<TreeSet<Plant>> plantsPerLine;
    List<TreeSet<Zombie>> zombiesPerLine;

    SortedSet<Plant> deadPlants = new TreeSet<>();
    SortedSet<Zombie> deadZombies = new TreeSet<>();
    SortedSet<Plant> deadPlantsLastTurn = new TreeSet<>();
    SortedSet<Zombie> deadZombiesLastTurn = new TreeSet<>();

    Integer zombiesKilled = 0;
    Integer plantsKilled = 0;

    public PlayGroundData(Integer length, Line[] lines) {
        this.width = lines.length;
        this.length = length;
        this.lines = lines;
        plantsPerLine = new ArrayList<>(width);
        zombiesPerLine = new ArrayList<>(width);
    }


}
