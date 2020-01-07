package creature.being.zombie;

import creature.being.BeingDna;
import line.LineState;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ZombieDna extends BeingDna {
    private static List<ZombieDna> allDnas;

    public static List<ZombieDna> getAllDnas() {
        return allDnas;
    }

    public static void loadFromData(String json) {
        Gson gson = new Gson();
        allDnas = gson.fromJson(json, new TypeToken<List<ZombieDna>>(){}.getType());
    }

    private final ZombieDna whenIDie;
    private final ArrayList <Integer> crossing;
    private final int maxNumberOfJumps = 0;
    private final boolean haveGlove;
    private final boolean magnetic;
    private final boolean haveHat;

    public ZombieDna getWhenIDie() {
        return whenIDie;
    }

    public ArrayList<Integer> getCrossing() {
        return crossing;
    }

    public int getMaxNumberOfJumps() {
        return maxNumberOfJumps;
    }

    public boolean isHaveGlove() {
        return haveGlove;
    }

    public boolean isMagnetic() {
        return magnetic;
    }

    public boolean isHaveHat() {
        return haveHat;
    }

    public ZombieDna(String name, int speed, boolean leftToRight, int powerOfDestruction, int shopPrice,
            int gamePrice, int firstHealth, ZombieDna whenIDie, ArrayList<Integer> crossing, boolean haveGlove,
            boolean magnetic, boolean haveHat, LineState lineState) {
        super(name, speed, leftToRight, powerOfDestruction, shopPrice, gamePrice, firstHealth, lineState);
        this.whenIDie = whenIDie;
        this.crossing = crossing;
        this.haveGlove = haveGlove;
        this.magnetic = magnetic;
        this.haveHat = haveHat;
    }
    
}
