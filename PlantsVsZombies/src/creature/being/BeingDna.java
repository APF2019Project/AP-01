package creature.being;

import creature.Dna;

public class BeingDna extends Dna {

  private int shopPrice;
  private int gamePrice;
  private int firstHealth;

  public int getShopPrice() {
    return shopPrice;
  }
  public int getGamePrice() {
    return gamePrice;
  }
  public int getFirstHealth() {
    return firstHealth;
  }

  public BeingDna(String name, String image, int speed, int powerOfDestruction, int shopPrice, int gamePrice,
      int firstHealth) {
    super(name, image, speed, powerOfDestruction);
    this.shopPrice = shopPrice;
    this.gamePrice = gamePrice;
    this.firstHealth = firstHealth;
  }

}