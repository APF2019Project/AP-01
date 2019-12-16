package player.zombie_player;

import exception.EndGameException;

public interface ZombiePlayer {
    void nextTurn() throws EndGameException;

}
