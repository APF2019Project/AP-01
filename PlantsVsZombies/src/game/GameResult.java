package game;

import exception.Winner;
import page.Message;
import page.Page;
import util.Result;
import util.Unit;

public class GameResult implements Page<Unit> {
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

    public Result<Unit> action() {
        if (winner == null) {
            Message.show("You abort the game!");
            return Result.ok();
        }
        if (gameMode == GameMode.PVP) {
            Message.show("Game has been finished");
            return Result.ok();
        }
        int moneyDelta = 0;
        int scoreDelta = 0;
        boolean isWin = false;
        if (gameMode != GameMode.ZOMBIE) {
            isWin = winner == Winner.PLANTS;
            scoreDelta = zombiesKilled;
            moneyDelta = zombiesKilled * 10;
        }
        else {
            isWin = winner == Winner.ZOMBIES;
            scoreDelta = plantsKilled;
            moneyDelta = plantsKilled * 10;
        }
        String res = (isWin ? "You won :)" : "You lose :(") + "\n";
        res += "Score gained: " + scoreDelta + "\n";
        res += "Money gained: " + moneyDelta + "\n";
        Message.show(res);
        return Result.ok();
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