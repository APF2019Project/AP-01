package gamedata;

import exception.InvalidGameMoveException;
import exception.NotImplementedException;
import line.Line;

public class PlayGroundData {

    private static PlayGroundData currentGameData;
    private Line[] lines;

    public static PlayGroundData getCurrentGameData() {
        return currentGameData;
    }

    public void NewZombie() throws NotImplementedException {
        throw new NotImplementedException("playGroundData.newZombie");
    }

    public void newPlant() throws InvalidGameMoveException, NotImplementedException {
        throw new NotImplementedException("platGroundData.newPlant");
    }

    public void newAmmunition() throws NotImplementedException {
        throw new NotImplementedException("platGroundData.newAmmunition");
    }

    

}
