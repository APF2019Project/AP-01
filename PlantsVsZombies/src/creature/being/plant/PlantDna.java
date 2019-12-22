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
  private final int canContain;
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

  public int getCanContain() {
    return canContain;
  }

  public boolean isExplosive() {
    return Explosive;
  }

  public PlantDna(String name, String image, int speed, int powerOfDestruction, int shopPrice, int gamePrice,
      int firstHealth, AmmunitionDna ammunitionDna, int sunIncome, int cooldown, int canContain, boolean explosive) {
    super(name, image, speed, powerOfDestruction, shopPrice, gamePrice, firstHealth);
    this.ammunitionDna = ammunitionDna;
    this.sunIncome = sunIncome;
    this.cooldown = cooldown;
    this.canContain = canContain;
    Explosive = explosive;
  }


}