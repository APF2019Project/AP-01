package creature.being.zombie;

import java.util.List;

import creature.Dna;

public class ZombieDna extends Dna {
    public ZombieDna(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    private static List<ZombieDna> allDnas;

    public static List<ZombieDna> getAllDnas() {
        return allDnas;
    }
}
