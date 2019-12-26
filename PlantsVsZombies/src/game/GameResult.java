package game;


import exception.Winner;
import page.Message;

public class GameResult {
    public final Winner winner;
    public final Integer plantsKilled;
    public final Integer zombiesKilled;
    public final GameMode gameMode;

    public GameResult(GameMode gameMode, Winner winner, Integer plantsKilled, Integer zombiesKilled) {
        this.winner = winner;
        this.plantsKilled = plantsKilled;
        this.zombiesKilled = zombiesKilled;
        this.gameMode = gameMode;
    }

    public void action() {
        Message.show(toString());
    }

    @Override
    public String toString() {
        if (winner == null) return "You abort the game!";
        return "GameResult{" +
                "winner=" + winner +
                ", plantsKilled=" + plantsKilled +
                ", zombiesKilled=" + zombiesKilled +
                '}';
    }
}