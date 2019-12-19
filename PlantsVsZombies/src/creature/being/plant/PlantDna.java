package creature.being.plant;

import creature.being.BeingDna;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.*;

public class PlantDna extends BeingDna {

  private static List<PlantDna> allDnas;

  public static List<PlantDna> getAllDnas() {
    ArrayList<PlantDna> options = new ArrayList<>();
      options.add(PlantDna.fromJson("{\"name\":\"tofangi\"}"));
      options.add(new PlantDna("bombi"));
      options.add(new PlantDna("khari"));
      options.add(new PlantDna("gol"));
    return options;
  }

  public static PlantDna fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, PlantDna.class);
  }

  public static void loadFromData() {
    try (Stream<Path> paths = Files.walk(Paths.get("/home/you/Desktop"))) {
      paths
        .filter(Files::isRegularFile)
        .forEach(System.out::println);
    }
    catch(IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
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