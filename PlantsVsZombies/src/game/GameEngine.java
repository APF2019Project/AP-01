package game;

import gamedata.PlayGroundData;

public class GameEngine {
    private static GameEngine currnetGameEngine;
    private GameState gameState = GameState.LOADING;
    private PlayGroundData DATABASE;

    public static GameEngine getCurrnetGameEngine() {
        return currnetGameEngine;
    }


}
