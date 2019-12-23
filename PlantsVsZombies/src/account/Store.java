package account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import creature.Dna;
import creature.being.BeingDna;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;

public class Store implements Serializable {
  private static final long serialVersionUID = 5608599943348289833L;
  // private Account owner;
  private Set<String> myCard = new HashSet<>();
  public int money = 1000;
  public void buyCard(BeingDna card) {
    myCard.add(card.getName());
    money -= card.getShopPrice();
  }

  public boolean haveCard(Dna card) {
    return myCard.contains(card.getName());
  }

  public Store(Account owner) {
    List<BeingDna> allDna = new ArrayList<>();
    allDna.addAll(PlantDna.getAllDnas());
    allDna.addAll(ZombieDna.getAllDnas());
    for (BeingDna x: allDna) {
      if (x.getShopPrice() == 0) myCard.add(x.getName());
    }
  }
}