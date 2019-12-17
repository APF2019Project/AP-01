package creature.being.plant;

import creature.being.BeingDna;

import java.util.List;

public class PlantDna extends BeingDna {

  private static List<PlantDna> allDnas;

  public static List<PlantDna> getAllDnas() {
    return allDnas;
  }

  public PlantDna(String name) {
    super(name);
    // TODO Auto-generated constructor stub
  }

    public Integer getCoolDown() {
        return null;
    }
}