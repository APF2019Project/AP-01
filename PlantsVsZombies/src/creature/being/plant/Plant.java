package creature.being.plant;

import creature.Location;
import creature.Creature;

public class Plant extends Creature {
    private final PlantDna plantDna;
    private int remainingCooldown = 0;
    private int remainingAmmunitionCooldown;

    public void createAmmunition() {

    }

    public void nextTurn() {
        super.nextTurn();
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
