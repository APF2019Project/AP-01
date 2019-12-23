package creature.being.zombie;

import creature.being.BeingDna;

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

    private final ZombieDna whenIDie = null;
    private final ArrayList <Integer> crossing = new ArrayList<>();
    private final int maxNumberOfJumps = 0;
    private final boolean haveGlove = false;
    private final boolean magnetic = false;
    private final boolean haveHat = false;

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

    public ZombieDna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction, int shopPrice,
            int gamePrice, int firstHealth, ZombieDna whenIDie, ArrayList<Integer> crossing, int maxNumberOfJumps,
            boolean haveGlove, boolean magnetic, boolean haveHat) {
        super(name, image, speed, leftToRight, powerOfDestruction, shopPrice, gamePrice, firstHealth);
    }

    

}
