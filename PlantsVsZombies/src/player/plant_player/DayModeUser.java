package player.plant_player;

import creature.being.plant.PlantDna;

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


    }

    @Override
    public void showHand() {

    }

    // TODO: implement this shit =)
}
