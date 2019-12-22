package creature.ammunition;
import creature.Dna;

public class AmmunitionDna extends Dna {
  private int cooldown;
  private int stunTurnNumber;
  private int reduceSpeedRatio;
  private int minimumDistanceForShoot;
  private boolean crossingShield;

  public int getCooldown() {
    return cooldown;
  }

  public void setCooldown(int cooldown) {
    this.cooldown = cooldown;
  }

  public int getStunTurnNumber() {
    return stunTurnNumber;
  }

  public void setStunTurnNumber(int stunTurnNumber) {
    this.stunTurnNumber = stunTurnNumber;
  }

  public int getReduceSpeedRatio() {
    return reduceSpeedRatio;
  }

  public void setReduceSpeedRatio(int reduceSpeedRatio) {
    this.reduceSpeedRatio = reduceSpeedRatio;
  }

  public int getMinimumDistanceForShoot() {
    return minimumDistanceForShoot;
  }

  public void setMinimumDistanceForShoot(int minimumDistanceForShoot) {
    this.minimumDistanceForShoot = minimumDistanceForShoot;
  }

  public boolean isCrossingShield() {
    return crossingShield;
  }

  public void setCrossingShield(boolean crossingShield) {
    this.crossingShield = crossingShield;
  }

  public AmmunitionDna(String name, String image, int speed, int powerOfDestruction, int cooldown, int stunTurnNumber,
      int reduceSpeedRatio, int minimumDistanceForShoot, boolean crossingShield) {
    super(name, image, speed, powerOfDestruction);
    this.cooldown = cooldown;
    this.stunTurnNumber = stunTurnNumber;
    this.reduceSpeedRatio = reduceSpeedRatio;
    this.minimumDistanceForShoot = minimumDistanceForShoot;
    this.crossingShield = crossingShield;
  }
}