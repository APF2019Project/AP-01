package creature.ammunition;
import creature.Dna;

public class AmmunitionDna extends Dna {
  private final int type;
  private final int cooldown;
  private final int sunIncome;
  private final int stunTurnNumber;
  private final int effectiveRange;
  private final int reduceSpeedRatio;
  private final int minimumDistanceForShoot;
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
  
  public boolean isJustKillShield() {
    return justKillShield;
  }

  public int getType() {
    return type;
  }

  public int getSunIncome() {
    return sunIncome;
  }

  public AmmunitionDna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction, int type,
      int cooldown, int sunIncome, int stunTurnNumber, int effectiveRange, int reduceSpeedRatio,
      int minimumDistanceForShoot, boolean justKillShield) {
    super(name, image, speed, leftToRight, powerOfDestruction);
    this.type = type;
    this.cooldown = cooldown;
    this.sunIncome = sunIncome;
    this.stunTurnNumber = stunTurnNumber;
    this.effectiveRange = effectiveRange;
    this.reduceSpeedRatio = reduceSpeedRatio;
    this.minimumDistanceForShoot = minimumDistanceForShoot;
    this.justKillShield = justKillShield;
  }
  
}