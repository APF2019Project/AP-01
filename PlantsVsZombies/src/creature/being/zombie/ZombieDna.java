package creature.being.zombie;

import creature.being.BeingDna;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ZombieDna extends BeingDna {
    private static List<ZombieDna> allDnas;

    public static List<ZombieDna> getAllDnas() {
        return allDnas;
    }

    public static void loadFromData(String json) {
        Gson gson = new Gson();
        allDnas = gson.fromJson(json, new TypeToken<List<ZombieDna>>(){}.getType());
    }

    private ZombieDna whenIDie;

    public ZombieDna getWhenIDie() {
        return whenIDie;
    }

    public ZombieDna(String name, String image, int speed, int powerOfDestruction, int shopPrice, int gamePrice,
            int firstHealth, ZombieDna whenIDie) {
        super(name, image, speed, powerOfDestruction, shopPrice, gamePrice, firstHealth);
        this.whenIDie = whenIDie;
    }
    
}
