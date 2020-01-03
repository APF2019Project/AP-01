package game;

import account.Account;
import exception.Winner;
import page.Message;
import page.Page;
import util.Effect;
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

    public Effect<Unit> action() {
        if (winner == null) {
            Message.show("You abort the game!");
            return Effect.ok();
        }
        if (gameMode == GameMode.PVP) {
            Message.show("Game has been finished");
            return Effect.ok();
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
        if (gameMode == GameMode.RAIL) res = "Game over!\n";
        res += "Score gained: " + scoreDelta + "\n";
        res += "Money gained: " + moneyDelta + "$\n";
        Account.getCurrentAccount().getStore().money += moneyDelta;
        Account.getCurrentAccount().score += scoreDelta;
        Message.show(res);
        return Effect.ok();
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