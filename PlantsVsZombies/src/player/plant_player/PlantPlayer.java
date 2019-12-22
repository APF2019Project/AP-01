package player.plant_player;

import exception.EndGameException;

public interface PlantPlayer {
    void nextTurn() throws EndGameException;
    void showHand();
}
