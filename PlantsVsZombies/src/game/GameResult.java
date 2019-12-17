package game;

import exception.Winner;

public class GameResult {
    public final Winner winner;
    public final Integer plantsKilled;
    public final Integer zombiesKilled;

    public GameResult(Winner winner, Integer plantsKilled, Integer zombiesKilled) {
        this.winner = winner;
        this.plantsKilled = plantsKilled;
        this.zombiesKilled = zombiesKilled;
    }
}