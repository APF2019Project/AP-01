package player.plant_player;

import account.Account;
import creature.being.plant.PlantDna;
import game.GameEngine;

import java.util.List;
import java.util.Random;

public class ZombieModeAI implements PlantPlayer {


    private GameEngine gameEngine;
    private Random random;

    public ZombieModeAI() {
        gameEngine = GameEngine.getCurrentGameEngine();
        random = GameEngine.getCurrentGameEngine().getRandom();
    }

    @Override
    public void nextTurn() {
        if (!gameEngine.getTurn().equals(0)) return;
        List<PlantDna> allDnas = Account.getCurrentUserPlants();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < gameEngine.getWidth(); j++) {
                try {
                    gameEngine.newPlant(allDnas.get(random.nextInt(allDnas.size())), j, i);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void showHand() {
        throw new UnsupportedOperationException("there is no hand =)!");
    }
}
