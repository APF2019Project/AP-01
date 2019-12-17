package creature.being.plant;

import creature.being.BeingDna;

import java.util.ArrayList;
import java.util.List;

public class PlantDna extends BeingDna {

  private static List<PlantDna> allDnas;

  public static List<PlantDna> getAllDnas() {
    ArrayList<PlantDna> options = new ArrayList<>();
      options.add(new PlantDna("tofangi"));
      options.add(new PlantDna("bombi"));
      options.add(new PlantDna("khari"));
      options.add(new PlantDna("gol"));
    return options;
  }

  private Integer sun = null;

  public Integer getSun() {
    return sun;
  }

  public PlantDna(String name) {
    super(name);
    // TODO Auto-generated constructor stub
  }

    public Integer getCoolDown() {
        return null;
    }
}