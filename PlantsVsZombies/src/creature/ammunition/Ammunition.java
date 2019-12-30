package creature.ammunition;

import creature.Creature;
import creature.Location;
import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import exception.EndGameException;

import java.util.List;
import java.util.SortedSet;

//TODO تیر سر جای خود ایستاد و از گیاه عبور نکرد و قفلی زد

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

    private boolean move() {
        int direction = 1;
        if (ammunitionDna.getSpeed() < 0) direction = -1;
        for (int i = 0; i != ammunitionDna.getSpeed(); i += direction) {
            List<Zombie> zombies = gameEngine.getZombies(location);
            boolean findZombie = zombies.stream().anyMatch(zombie ->
                    zombie.getZombieDna().getCrossing().indexOf(ammunitionDna.getType()) == -1);
            if (findZombie) return true;
            try {
                location = location.nextLocation(direction);
            } catch (Exception exp) {
                gameEngine.killAmmunition(this);
                return false;
            }
        }
        return !gameEngine.getZombies(location).isEmpty();
    }

    @Override
    public void nextTurn() throws EndGameException {
        if (ammunitionDna.getType() == 0) {
            gameEngine.addSun(ammunitionDna.getSunIncome());
            gameEngine.killAmmunition(this);
            return;
        }
        if (!move()) return;
        SortedSet<Zombie> zombies = gameEngine.getZombies(location.lineNumber);
        zombies.forEach(zombie -> {
            int dis = Math.max(
                    Math.abs(location.lineNumber - zombie.getLocation().lineNumber),
                    Math.abs(location.position - zombie.getLocation().position)
            );
            if (zombie.getLocation().position == location.position ||
                    owner.getPlantDna().isExplosive() && dis <= ammunitionDna.getEffectiveRange()) {
                try {
                    effect(zombie);
                } catch (Exception exception) {
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