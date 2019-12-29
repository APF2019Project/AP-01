package creature.being.plant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import creature.ammunition.AmmunitionDna;
import creature.being.BeingDna;

import java.util.ArrayList;
import java.util.List;

public class PlantDna extends BeingDna {

  private static List<PlantDna> allDnas;
  private final ArrayList<AmmunitionDna> ammunitionDna;
  private final int cooldown;
  private final boolean canContain;
  private final boolean Explosive;

  public PlantDna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction, int shopPrice,
                  int gamePrice, int firstHealth, ArrayList<AmmunitionDna> ammunitionDna, int cooldown,
                  int productionNumberOfAmmunitionPerUse, boolean canContain, boolean explosive) {
    super(name, image, speed, leftToRight, powerOfDestruction, shopPrice, gamePrice, firstHealth);
    this.ammunitionDna = ammunitionDna;
    this.cooldown = cooldown;
    this.canContain = canContain;
    Explosive = explosive;
  }

  public static List<PlantDna> getAllDnas() {
    return allDnas;
  }

  public static void loadFromData(String json) {
    Gson gson = new Gson();
    allDnas = gson.fromJson(json, new TypeToken<List<PlantDna>>() {
    }.getType());
  }

  public List<AmmunitionDna> getAmmunitionDna() {
    return ammunitionDna;
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

  @Override
  public String toString() {
    return "-------------------------------------------------------------------------------" + "\n" +
            "name: " + getName() + "\n" +
            "Health: " + getFirstHealth() + "\n" +
            "cool down: " + getCooldown() + "\n" +
            "price: " + getGamePrice();
  }
}