package player.plant_player;

import creature.being.plant.PlantDna;
import page.menu.ActionButton;
import page.menu.Menu;

import java.util.Random;

public class DayModeUser implements PlantPlayer {

    private Integer sun = 2;
    private Random rnd;
    private PlantDna[] plantDnas;
    private Integer[] lastTimeUsed = new Integer[7];

    private void randomSunAdder() {
        Integer added = rnd.nextInt(2) * (2 + rnd.nextInt(4));
        sun += added;
    }

    @Override
    public void nextTurn() {
        randomSunAdder();
        for (int i = 0; i < 7; i++) lastTimeUsed[i] += 1;
        new Menu<>(
            new ActionButton<>("Show hand", () -> showHand()),
            new ActionButton<>("Show lane", () -> showLane()),
            new ActionButton<>("End Turn", () -> showHand())
        ).action();
    }

    private Object showLane() {
        return null;
    }

    @Override
    public void showHand() {

    }

    // TODO: implement this shit =)
}
