package account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import creature.Dna;
import creature.being.BeingDna;

public class Store implements Serializable {
  private static final long serialVersionUID = 5608599943348289833L;
  // private Account owner;
  private Set<String> myCard = new HashSet<>();
  public int money = 15;
  public void buyCard(BeingDna card) {
    myCard.add(card.getName());
    money -= card.getPrice();
  }

  public boolean haveCard(Dna card) {
    return myCard.contains(card.getName());
  }

  public Store(Account owner) {
  }
}