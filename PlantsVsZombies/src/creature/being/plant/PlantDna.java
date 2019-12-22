package creature.being.plant;

import creature.being.BeingDna;
import main.Program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.lang.model.element.TypeElement;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PlantDna extends BeingDna {

  private static List<PlantDna> allDnas;

  public static List<PlantDna> getAllDnas() {
    return allDnas;
  }

  public static void loadFromData(String json) {
    Gson gson = new Gson();
    allDnas = gson.fromJson(json, new TypeToken<List<PlantDna>>(){}.getType());
  }

  private Integer sun = null;

  public Integer getSun() {
    return sun;
  }

  public PlantDna(String name, int price) {
    super(name, price);
    // TODO Auto-generated constructor stub
  }

    public Integer getCoolDown() {
        return null;
    }
}