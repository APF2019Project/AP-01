package account;

import java.io.Serializable;
import java.util.ArrayList;

import creature.Dna;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;

public class Store implements Serializable {
  private static final long serialVersionUID = 5608599943348289833L;
  // private Account owner;
  private ArrayList<String> myPlants;
  private ArrayList<String> myZombies;
  public int money;
  public void buyCard(Dna card) {
    if (card instanceof PlantDna) {
      myPlants.add(card.getName());
    }
    if (card instanceof ZombieDna) {
      myZombies.add(card.getName());  
    }
  }

  public Store(Account owner) {
    //this.owner = owner;
    myPlants = new ArrayList<>();
    myZombies = new ArrayList<>();
  }
}