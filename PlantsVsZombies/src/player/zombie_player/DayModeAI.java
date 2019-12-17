package player.zombie_player;

import creature.being.zombie.ZombieDna;
import exception.EndGameException;
import exception.Winner;
import game.GameEngine;

import java.util.List;
import java.util.Random;

enum AttackState {
    ATTACKING,
    WAITING
}

public class DayModeAI implements ZombiePlayer {
    private GameEngine gameEngine;
    private Random random;
    private Integer waveNumber = 1;
    private Integer counter = 0;
    private AttackState attackState = AttackState.WAITING;

    public DayModeAI() {
        gameEngine = GameEngine.getCurrentGameEngine();
        random = GameEngine.getCurrentGameEngine().getRandom();
    }

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
            counter++;
            if (counter == 3) {
                attackState = AttackState.WAITING;
                counter = 0;
            }
        } else {
            counter++;
            if (!gameEngine.getZombies().isEmpty())
                counter = 0;
            if (waveNumber == 1) {
                if (counter == 3) {
                    attackState = AttackState.ATTACKING;
                    waveNumber++;
                    counter = 0;
                }
            } else if (waveNumber < 4) {
                if (counter == 7) {
                    attackState = AttackState.ATTACKING;
                    waveNumber++;
                    counter = 0;
                }
            } else if (waveNumber == 4 && counter > 0)
                throw new EndGameException(Winner.Plants);
        }
    }

    @Override
    public void showHand() {
        throw new UnsupportedOperationException("zombie ai can't show hand =)!");
    }
}
