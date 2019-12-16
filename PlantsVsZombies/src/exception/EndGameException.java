package exception;

public class EndGameException extends Exception {
    private final Winner winner;

    public EndGameException(Winner winner) {
        this.winner = winner;
    }

    public Winner getWiner() {
        return winner;
    }
}
