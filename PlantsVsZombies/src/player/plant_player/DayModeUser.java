package player.plant_player;

import java.util.Random;

public class DayModeUser implements PlantPlayer {

    private Integer sun = 2;
    private Random rnd;
    private PlantDNA[] plantDNAS;
    private Integer[] lastTimeUsed = new Integer[7];

    private void randomSunAdder() {
        Integer added = rnd.nextInt(2) * (2 + rnd.nextInt(4));
        sun += added;
    }

    @Override
    public void nextTurn() {
        randomSunAdder();
        for (int i = 0; i < 7; i++) lastTimeUsed[i] += 1;
        // TODO: menu should be added

    }

    // TODO: implement this shit =)
}
