package creature.ammunition;
import creature.Dna;

public class AmmunitionDna extends Dna {
  private final int cooldown;
  private final int stunTurnNumber;
  private final int effectiveRange;
  private final int reduceSpeedRatio;
  private final int minimumDistanceForShoot;
  private final boolean crossingShield;
  private final boolean justKillShield;

  public int getCooldown() {
    return cooldown;
  }

  public int getStunTurnNumber() {
    return stunTurnNumber;
  }

  public int getEffectiveRange() {
    return effectiveRange;
  }

  public int getReduceSpeedRatio() {
    return reduceSpeedRatio;
  }

  public int getMinimumDistanceForShoot() {
    return minimumDistanceForShoot;
  }

  public boolean isCrossingShield() {
    return crossingShield;
  }

  public boolean isJustKillShield() {
    return justKillShield;
  }

  public AmmunitionDna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction, int cooldown,
      int stunTurnNumber, int effectiveRange, int reduceSpeedRatio, int minimumDistanceForShoot, boolean crossingShield,
      boolean justKillShield) {
    super(name, image, speed, leftToRight, powerOfDestruction);
    this.cooldown = cooldown;
    this.stunTurnNumber = stunTurnNumber;
    this.effectiveRange = effectiveRange;
    this.reduceSpeedRatio = reduceSpeedRatio;
    this.minimumDistanceForShoot = minimumDistanceForShoot;
    this.crossingShield = crossingShield;
    this.justKillShield = justKillShield;
  }

  
}