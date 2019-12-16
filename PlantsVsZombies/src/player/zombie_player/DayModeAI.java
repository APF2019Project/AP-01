package player.zombie_player;

import creature.being.zombie.ZombieDna;
import exception.EndGameException;
import exception.Winner;
import game.GameEngine;

import java.util.List;
import java.util.Random;

private enum AttackState {
    ATTACKING,
    WAITING
}

public class DayModeAI implements ZombiePlayer {
    private GameEngine gameEngine = GameEngine.getCurrentGameEngine();
    private Random random = GameEngine.getCurrentGameEngine().getRandom();
    private Integer waveNumber = 1;
    private Integer Counter = 0;
    private AttackState attackState = AttackState.WAITING;

    private void attack() {
        List<ZombieDna> allDnas = ZombieDna.getAllDnas();
        int send = 1 + random.nextInt(2 * waveNumber);
        for (int i = 0; i < send; i++) {
            try {
                gameEngine.newZombie(allDnas.get(random.nextInt(allDnas.size())), random.nextInt(gameEngine.getWidth()));
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void nextTurn() throws EndGameException {
        if (attackState == AttackState.ATTACKING) {
            attack();
            Counter++;
            if (Counter == 3) {
                attackState = AttackState.WAITING;
                Counter = 0;
            }
        } else {
            Counter++;
            if (!gameEngine.getZombies().isEmpty())
                Counter = 0;
            if (waveNumber == 1) {
                if (Counter == 3) {
                    attackState = AttackState.ATTACKING;
                    waveNumber++;
                    Counter = 0;
                }
            } else if (waveNumber < 4) {
                if (Counter == 7) {
                    attackState = AttackState.ATTACKING;
                    waveNumber++;
                    Counter = 0;
                }
            } else if (waveNumber == 4 && Counter > 0)
                throw new EndGameException(Winner.Plants);
        }
    }
}
