package creature.ammunition;
import creature.Dna;
import game.GameEngine;

public class AmmunitionDna extends Dna {
  private final int type;
  private final int cooldown;
  private final int sunIncome;
  private final int firstHealth;
  private final int stunTurnNumber;
  private final int effectiveRange;
  private final int reduceSpeedRatio;
  private final int minimumDistanceForShoot;
  private final int productionNumberOfAmmunitionPerUse;
  private final int firstTimeHealth;

  public int getCooldown() {
      return cooldown * GameEngine.getFRAME() * 2 / 3;
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

  public int getType() {
    return type;
  }

  public int getSunIncome() {
    return sunIncome;
  }

  public int getFirstHealth() {
    return firstHealth;
  }

  public int getProductionNumberOfAmmunitionPerUse() {
    return productionNumberOfAmmunitionPerUse;
  }

  public int getFirstTimeHealth() {
    return firstTimeHealth;
  }

  public AmmunitionDna(String name, int speed, boolean leftToRight, int powerOfDestruction, int type,
      int cooldown, int sunIncome, int firstHealth, int stunTurnNumber, int effectiveRange, int reduceSpeedRatio,
      int minimumDistanceForShoot, int productionNumberOfAmmunitionPerUse,
      int firstTimeHealth) {
    super(name, speed, powerOfDestruction);
    this.type = type;
    this.cooldown = cooldown;
    this.sunIncome = sunIncome;
    this.firstHealth = firstHealth;
    this.stunTurnNumber = stunTurnNumber;
    this.effectiveRange = effectiveRange;
    this.reduceSpeedRatio = reduceSpeedRatio;
    this.minimumDistanceForShoot = minimumDistanceForShoot;
    this.productionNumberOfAmmunitionPerUse = productionNumberOfAmmunitionPerUse;
    this.firstTimeHealth = firstTimeHealth;
  }
  
}