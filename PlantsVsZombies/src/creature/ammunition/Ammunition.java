package creature.ammunition;

import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import exception.EndGameException;
import page.Message;
import creature.Creature;
import creature.Location;

import java.util.*;

public class Ammunition extends Creature {
    private final AmmunitionDna ammunitionDna;
    private final Plant owner;

    public void effect(Zombie zombie) throws Exception {
        if (ammunitionDna.isJustKillShield() && 
            zombie.getZombieDna().getWhenIDie() == null) 
                return;
        
        if (zombie.reduceHealth(ammunitionDna.getPowerOfDestruction(), ammunitionDna.getType())) {
            gameEngine.killZombie(zombie);
        }
        zombie.reduceSpeed(ammunitionDna.getReduceSpeedRatio(), ammunitionDna.getStunTurnNumber());

        health--;
        if (health == 0) throw new Exception("I Killed");
    }

    public void nextTurn() throws EndGameException {
        if (ammunitionDna.getType() == 0) {
            gameEngine.addSun(ammunitionDna.getSunIncome());
            gameEngine.killAmmunition(this);
            return;
        }
        SortedSet <Zombie> zombies = gameEngine.getZombies(location.lineNumber);
        zombies.forEach(zombie -> {
            int dis = Math.max(
                Math.abs(location.lineNumber - zombie.getLocation().lineNumber),
                Math.abs(location.position - zombie.getLocation().position)
            );
            if (zombie.getLocation().position == location.position || 
                owner.getPlantDna().isExplosive() && dis <= ammunitionDna.getEffectiveRange())
                    
                 {
                try {
                    effect(zombie);
                }
                catch (Exception exception) {
                    gameEngine.killAmmunition(this);
                    return;
                }
            }
        });
    }

    public Ammunition(Location location, AmmunitionDna ammunitionDna, Plant owner) {
        super(location);
        this.ammunitionDna = ammunitionDna;
        this.owner = owner;
        this.health = ammunitionDna.getFirstHealth();
    }
    
}