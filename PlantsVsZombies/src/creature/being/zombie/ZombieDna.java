package creature.being.zombie;

import creature.being.BeingDna;

import java.util.List;

public class ZombieDna extends BeingDna {
    private static List<ZombieDna> allDnas;

    public ZombieDna(String name) {
        super(name);
    }

    public static List<ZombieDna> getAllDnas() {
        return allDnas;
    }
}
