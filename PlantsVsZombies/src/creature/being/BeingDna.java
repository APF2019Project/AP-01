package creature.being;

import creature.Dna;

public class BeingDna extends Dna {

  protected int price;

  public int getPrice() {
    return price;
  }

  public BeingDna(String name, int price) {
    super(name);
    this.price = price;
    // TODO Auto-generated constructor stub
  }

}