package game;

import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import line.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PlayGroundData {

    public final Integer width;
    public final Integer length;

    public TreeSet<Plant> plants = new TreeSet<>();
    public TreeSet<Zombie> zombies = new TreeSet<>();
    public Line[] lines;

    public List<TreeSet<Plant>> plantsPerLine;
    public List<TreeSet<Zombie>> zombiesPerLine;

    public PlayGroundData(Integer length, Line[] lines) {
        this.width = lines.length;
        this.length = length;
        this.lines = lines;
        plantsPerLine = new ArrayList<>(width);
        zombiesPerLine = new ArrayList<>(width);
    }


}
