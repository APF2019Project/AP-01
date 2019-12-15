package game;

import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import line.Line;

import java.util.TreeSet;

public class PlayGroundData {

    public final Integer weidth;
    public final Integer length;

    public TreeSet<Plant> plants = new TreeSet<>();
    public TreeSet<Zombie> zombies = new TreeSet<>();
    public Line[] lines;

    public TreeSet<Plant>[] plantsPerLine;
    public TreeSet<Zombie>[] zombiesPerLine;

    public PlayGroundData(Integer length, Line[] lines) {
        this.weidth = lines.length;
        this.length = length;
        plantsPerLine = new TreeSet<Plant>[weidth];
        zombiesPerLine = new TreeSet<Zombie>[weidth];
    }


}
