package creature.being.plant;

import creature.Creature;
import creature.Location;
import creature.being.zombie.Zombie;
import exception.EndGameException;
import jdk.jshell.spi.ExecutionControl.ExecutionControlException;

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
        if (plantDna.getAmmunitionDna().isEmpty()) return;
        plantDna.getAmmunitionDna().forEach(ammunitionDna -> {
            int Number = ammunitionDna.getProductionNumberOfAmmunitionPerUse();
            if (Number == 3) {
                for (int dx = -1; dx <= 1; dx++) {
                    try {
                        gameEngine.newAmmunition(this.location.moveBy(dx, 0), ammunitionDna, this);
                    }
                    catch(Exception exception) {
                        
                    }
                }
            }
            else if (Number == 9) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        try {
                            gameEngine.newAmmunition(
                                this.location.moveBy(dx, dy), ammunitionDna, this);
                        }
                        catch(Exception exception) {
                            
                        }
                    }
                }
            }
            else {
                for (int num = 0; num < Number; ++num) {
                    gameEngine.newAmmunition(this.location, ammunitionDna, this);
                }
            }
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
        if (remainingAmmunitionCooldown == 0) {
            if (!plantDna.getAmmunitionDna().isEmpty()) {
               remainingAmmunitionCooldown = plantDna.getAmmunitionDna().get(0).getCooldown();
                createAmmunition();
            }
        }
        else remainingAmmunitionCooldown--;
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
    }

    @Override
    public String toString() {
        return "Plant\ntype = " + plantDna.getName() + "\n" + super.toString() + "remainingAmmunitionCooldown=" + remainingAmmunitionCooldown + "\n\n";
    }

}
