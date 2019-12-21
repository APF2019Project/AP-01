package account;

import java.util.ArrayList;
import java.util.List;

import creature.being.BeingDna;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
import main.ConsoleColors;
import main.Program;
import page.Message;
import page.Page;
import util.Result;

public class ShopPage implements Page<Void> {

  @Override
  public Result<Void> action() {
    Store me = Account.current.store;
    while (true) {
      Program.clearScreen();
      System.out.println("Enter number to buy ( or e for exit )");
      System.out.println("Your money is: "+me.money+"$");
      int i=0;
      ArrayList<BeingDna> buyOptions = new ArrayList<>();
      List<BeingDna> allDna = new ArrayList<>();
      allDna.addAll(PlantDna.getAllDnas());
      allDna.addAll(ZombieDna.getAllDnas());
      for (BeingDna x: allDna) {
        if (me.haveCard(x))
          System.out.println("X. " + x.getName() + "     sold out");
        else if (me.money < x.getPrice()) {
          System.out.println("X. " + x.getName() + "     " + ConsoleColors.red(x.getPrice()+"$"));
        } 
        else {
          System.out.println(i + ". " + x.getName() + "     " + x.getPrice()+"$");
          buyOptions.add(x);
          i++;  
        }
      }
      String s = Program.scanner.nextLine();
      if (s.equals("e")) return Result.error("end");
      try{
        me.buyCard(buyOptions.get(Integer.parseInt(s)));
      }
      catch(Exception e) {
        Message.show("Please enter a valid number");
      }
    }
  }
}