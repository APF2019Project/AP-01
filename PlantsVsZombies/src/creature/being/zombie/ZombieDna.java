package creature.being.zombie;

import creature.being.BeingDna;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ZombieDna extends BeingDna {
    public ZombieDna(String name, int price) {
        super(name, price);
        // TODO Auto-generated constructor stub
    }

    private static List<ZombieDna> allDnas;

    public static List<ZombieDna> getAllDnas() {
        return allDnas;
    }

    public static void loadFromData(String json) {
        Gson gson = new Gson();
        allDnas = gson.fromJson(json, new TypeToken<List<ZombieDna>>(){}.getType());
    }
}
