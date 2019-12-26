package creature.being.plant;

import creature.Creature;
import creature.Location;
import creature.being.zombie.Zombie;
import exception.EndGameException;

public class Plant extends Creature {
    private final PlantDna plantDna;
    private int remainingFirstCooldown;
    private int remainingAmmunitionCooldown;
    //remainingCooldown unuse but good :)

    public void reduceHealth(int damageAmount) {
        health -= damageAmount;
        if (health <= 0) gameEngine.killPlant(this);
    }

    public void createAmmunition() {
        plantDna.getAmmunitionDna().forEach(ammunitionDna -> {
            gameEngine.newAmmunition(this.location, ammunitionDna, this);
        });
        if (plantDna.isExplosive()) gameEngine.killPlant(this);
    }

    public void damage(Zombie zombie) {
        if (plantDna.isExplosive()) {
            createAmmunition();
            return;
        }
        if (zombie.reduceHealth(plantDna.getPowerOfDestruction())) {
            gameEngine.killZombie(zombie);
        }
    }

    @Override
    public void nextTurn() throws EndGameException {
        super.nextTurn();
        if (remainingAmmunitionCooldown == 0 && !plantDna.getAmmunitionDna().isEmpty()) {
            remainingAmmunitionCooldown = plantDna.getAmmunitionDna().get(0).getCooldown();
            createAmmunition();
        }
    }

    public PlantDna getPlantDna() {
        return plantDna;
    }

    public int getRemainingFirstCooldown() {
        return remainingFirstCooldown;
    }

    public int getRemainingAmmunitionCooldown() {
        return remainingAmmunitionCooldown;
    }

    public void setRemainingAmmunitionCooldown(int remainingAmmunitionCooldown) {
        this.remainingAmmunitionCooldown = remainingAmmunitionCooldown;
    }

    public Plant(PlantDna plantDna, int lineNumber, int position) {
        super(new Location(lineNumber, position));
        this.plantDna = plantDna;
        if (!plantDna.getAmmunitionDna().isEmpty())
            this.remainingAmmunitionCooldown = plantDna.getAmmunitionDna().get(0).getCooldown();
        else
            this.remainingAmmunitionCooldown = 0;
        this.health = plantDna.getFirstHealth();
        this.remainingAmmunitionCooldown = plantDna.getFirstCooldown();
    }

    @Override
    public String toString() {
        return "Plant\ntype = " + plantDna.getName() + "\n" + super.toString() + "remainingAmmunitionCooldown=" + remainingAmmunitionCooldown + "\n\n";
    }

}
