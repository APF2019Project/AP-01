package account;

import creature.being.BeingDna;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
import main.ConsoleColors;
import main.Program;
import page.Message;
import page.Page;
import util.Effect;
import util.Unit;

import java.util.ArrayList;
import java.util.List;

public class ShopPage implements Page<Unit> {

  @Override
  public Effect<Unit> action() {
    return Effect.syncWork(()->{
      Store me = Account.current.store;
      while (true) {
        Program.clearScreen();
        System.out.println("Enter number to buy ( or e for exit )");
        System.out.println("Your money is: " + me.money + "$");
        int i = 0;
        ArrayList<BeingDna> buyOptions = new ArrayList<>();
        List<BeingDna> allDna = new ArrayList<>();
        allDna.addAll(PlantDna.getAllDnas());
        allDna.addAll(ZombieDna.getAllDnas());
        for (BeingDna x : allDna) {
          if (me.haveCard(x))
            System.out.println("X. " + x.getName() + "     sold out");
          else if (me.money < x.getShopPrice()) {
            System.out.println("X. " + x.getName() + "     " + ConsoleColors.red(x.getShopPrice() + "$"));
          } else {
            System.out.println(i + ". " + x.getName() + "     " + x.getShopPrice() + "$");
            buyOptions.add(x);
            i++;
          }
        }  
      }
    });
  }
}