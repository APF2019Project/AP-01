package creature.being.plant;

import creature.ammunition.AmmunitionDna;
import creature.being.BeingDna;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PlantDna extends BeingDna {

  private static List<PlantDna> allDnas;
  
  public static List <PlantDna> getAllDnas() {
    return allDnas;
  }
  public static void loadFromData(String json) {
    Gson gson = new Gson();
    allDnas = gson.fromJson(json, new TypeToken<List<PlantDna>>(){}.getType());
  }

  private final AmmunitionDna ammunitionDna;
  private final int sunIncome;
  private final int cooldown;
  private final int productionNumberOfAmmunitionPerUse;
  private final boolean canContain;
  private final boolean Explosive;

  public static void setAllDnas(List<PlantDna> allDnas) {
    PlantDna.allDnas = allDnas;
  }

  public AmmunitionDna getAmmunitionDna() {
    return ammunitionDna;
  }

  public int getSunIncome() {
    return sunIncome;
  }

  public int getCooldown() {
    return cooldown;
  }

  public boolean isExplosive() {
    return Explosive;
  }

  public boolean isCanContain() {
    return canContain;
  }

  public int getProductionNumberOfAmmunitionPerUse() {
    return productionNumberOfAmmunitionPerUse;
  }

  public PlantDna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction, int shopPrice,
      int gamePrice, int firstHealth, AmmunitionDna ammunitionDna, int sunIncome, int cooldown,
      int productionNumberOfAmmunitionPerUse, boolean canContain, boolean explosive) {
    super(name, image, speed, leftToRight, powerOfDestruction, shopPrice, gamePrice, firstHealth);
    this.ammunitionDna = ammunitionDna;
    this.sunIncome = sunIncome;
    this.cooldown = cooldown;
    this.productionNumberOfAmmunitionPerUse = productionNumberOfAmmunitionPerUse;
    this.canContain = canContain;
    Explosive = explosive;
  }

}