package creature;

public class Creature implements Comparable <Creature> {
    private Location location;
    protected final int id;

    private static int counter;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public void nextTurn() {

    }

    public Creature(Location location) {
        this.location = location;
        this.id = counter++;
    }

    @Override
    public int compareTo(Creature creature) {
        if (location.equals(creature.location)) return id - creature.id;
        return location.compareTo(creature.location);
    }

    @Override
    public String toString() {
        return "location = " + location.lineNumber + "   " + location.position + "\n";
    }

}