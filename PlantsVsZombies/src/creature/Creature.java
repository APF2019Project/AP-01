package creature;
import exception.EndGameException;
import game.GameEngine;

public class Creature implements Comparable <Creature> {
    protected Location location;
    protected GameEngine gameEngine;
    protected final int id;
    protected int health;

    private static int counter;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public void nextTurn() throws EndGameException {

    }

    public Creature(Location location) {
        this.location = location;
        this.id = counter++;
        gameEngine = GameEngine.getCurrentGameEngine();
    }

    @Override
    public int compareTo(Creature creature) {
       // if (location.equals(creature.location)) 
       return id - creature.id;
       // return location.compareTo(creature.location);
    }

    @Override
    public String toString() {
        return "location = " + location.lineNumber + "   " + location.position + "\n" +
        "health = " + health + "\n";
    }

}