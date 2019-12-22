package creature.being.zombie;

import creature.Location;
import creature.Creature;

public class Zombie extends Creature {
    private final ZombieDna zombieDna;
    
    public void move() {

    }

    public Location getLocation() {
        return null;
    }

    public Zombie(ZombieDna zombieDna, int lineNumber, int position) {
        super(new Location(lineNumber, position));
        this.zombieDna = zombieDna;
    }
    
    @Override
    public String toString() {
        return "Zombie\ntype = " + zombieDna.getName() + "\n" + super.toString() + "\n\n";
    }
}
