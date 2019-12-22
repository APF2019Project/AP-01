package creature.being.plant;

import creature.Location;
import creature.being.zombie.Zombie;
import exception.EndGameException;
import creature.Creature;

public class Plant extends Creature {
    private final PlantDna plantDna;
    private int remainingCooldown = 0;
    private int remainingAmmunitionCooldown;
    //unuse but good :)

    public void reduceHealth(int damageAmount) {
        health -= damageAmount;
        if (health <= 0) gameEngine.killPlant(this);
    }
    public void createAmmunition() {

        if (plantDna.isExplosive()) gameEngine.killPlant(this);
    }

    public void damage(Zombie zombie) {
        if (plantDna.isExplosive()) {
            createAmmunition();
            return;
        }
        zombie.reduceHealth(plantDna.getPowerOfDestruction());
    }

    @Override
    public void nextTurn() throws EndGameException {
        super.nextTurn();
        if (remainingAmmunitionCooldown == 0) {
            remainingAmmunitionCooldown = plantDna.getAmmunitionDna().getCooldown();
            createAmmunition();
        }
    }

    public PlantDna getPlantDna() {
        return plantDna;
    }

    public int getRemainingCooldown() {
        return remainingCooldown;
    }

    public void setRemainingCooldown(int remainingCooldown) {
        this.remainingCooldown = remainingCooldown;
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
        this.remainingAmmunitionCooldown = plantDna.getAmmunitionDna().getCooldown();
    }

    @Override
    public String toString() {
        return "Plant\ntype = " + plantDna.getName() + "\n" + super.toString() + "remainingAmmunitionCooldown=" + remainingAmmunitionCooldown + "\n remainingCooldown="
                + remainingCooldown + "\n\n";
    }
    
}
