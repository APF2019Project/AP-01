package creature.being.zombie;

import creature.Location;
import creature.Creature;
import creature.being.plant.Plant;
import exception.EndGameException;
import exception.Winner;
import java.util.*;

public class Zombie extends Creature {
    private final ZombieDna zombieDna;

    public void reduceHealth(int damageAmount) {
        health -= damageAmount;
        if (health <= 0) gameEngine.killZombie(this);
    }
    
    public Plant move() throws EndGameException {
        for (int i = 0; i < zombieDna.getSpeed(); i++) {
            Plant plant = gameEngine.getPlant(location);
            if (plant != null) return plant;
            try {
                location = location.left();
            }
            catch(Exception exp) {
                throw new EndGameException(Winner.Zombies);
            }
        }
        return gameEngine.getPlant(location);
    }

    @Override
    public void nextTurn() throws EndGameException {
        super.nextTurn();
        Plant plant = move();
        if (plant == null) return;
        plant.reduceHealth(zombieDna.getPowerOfDestruction());
        plant.damage(this);
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
