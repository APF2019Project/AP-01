package game;

import line.Line;

import java.util.TreeSet;

public class PlayGroundData {

    public final Integer weidth;
    public final Integer length;

    public TreeSet<Plant> plants = new TreeSet<Plant>();
    public TreeSet<Zombie> zombies = new TreeSet<Zombie>();
    public list[] lines;

    public TreeSet<Plant>[] plantsPerLine;
    public TreeSet<Zombie>[] zombiesPerLine;

    public PlayGroundData(Integer length, Line[] lines) {
        this.weidth = lines.length;
        this.length = length;
        plantsPerLine = new TreeSet<Plant>[weidth];
        zombiesPerLine = new TreeSet<Zombie>[weidth];
    }


}
