package creature.being.zombie;

import java.util.List;

import creature.Creature;
import creature.Location;
import creature.being.plant.Plant;
import exception.EndGameException;
import exception.Winner;
import creature.ammunition.Ammunition;

public class Zombie extends Creature {
    private final ZombieDna zombieDna;
    private final Zombie whenIDie;
    private int numberOfJump;
    private int remainingStunTurnNumber;
    private int speed;

    public void reduceSpeed(int reduceSpeedRatio, int stunTurnNumber) {
        if (stunTurnNumber == 0) return;
        remainingStunTurnNumber = stunTurnNumber;
        speed = Math.min(speed, zombieDna.getSpeed() / reduceSpeedRatio);
    }

    public boolean reduceHealth(int damageAmount) {
        health -= damageAmount;
        return health <= 0;
    }

    public boolean reduceHealth(int damageAmount, int ammunitionType) {
        if (zombieDna.getCrossing().indexOf(ammunitionType) != -1) {
            if (whenIDie == null) return false;
            return reduceHealth(damageAmount, ammunitionType);
        }
        if (reduceHealth(damageAmount)) {
            if (whenIDie == null) return true;
            whenIDie.location = location;
            gameEngine.addZombie(whenIDie);
            gameEngine.killZombie(this);
            return false;
        }
        return false;
    }

    public void damage(Plant plant) {
        plant.reduceHealth(zombieDna.getPowerOfDestruction());
        plant.damage(this);
    }
    
    private Plant move() throws EndGameException {
        for (int i = 0; i < zombieDna.getSpeed(); i++) {
            List <Ammunition> ammunitions = gameEngine.getAmmunitions(location);
            for (Ammunition ammunition: ammunitions) {
                try {
                    ammunition.effect(this);
                }
                catch (Exception exception) {
                    gameEngine.killZombie(this);
                    return null;
                }
            }
            Plant plant = gameEngine.getPlant(location);
            if (plant != null) {
                if (numberOfJump < zombieDna.getMaxNumberOfJumps()) {
                    numberOfJump++;
                }
                else {
                    return plant;
                }
            }
            try {
                location = location.left();
            }
            catch(Exception exp) {
                throw new EndGameException(Winner.ZOMBIES);
            }
        }
        return gameEngine.getPlant(location);
    }

    @Override
    public void nextTurn() throws EndGameException {
        super.nextTurn();
        Plant plant = move();
        if (plant != null) damage(plant);
        if (speed != zombieDna.getSpeed()) {
            remainingStunTurnNumber--;
            if (remainingStunTurnNumber == 0) speed = zombieDna.getSpeed();
        }
    }

    public Location getLocation() {
        return location;
    }

    
    @Override
    public String toString() {
        return "Zombie\ntype = " + zombieDna.getName() + "\n" + super.toString() + "\n\n";
    }

    public ZombieDna getZombieDna() {
        return zombieDna;
    }

    public int getNumberOfJumpingPlant() {
        return numberOfJump;
    }

    public Zombie(ZombieDna zombieDna, int lineNumber, int position) {
        super(new Location(lineNumber, position));
        this.zombieDna = zombieDna;
        if (zombieDna.getWhenIDie() != null)
            this.whenIDie = new Zombie (zombieDna.getWhenIDie(), lineNumber, position);
        else
            this.whenIDie = null;
        this.speed = zombieDna.getSpeed();
        this.health = zombieDna.getFirstHealth();
    }
}
