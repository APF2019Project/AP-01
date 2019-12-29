package creature.being;

import creature.Dna;

public class BeingDna extends Dna {

  private final int shopPrice;
  private final int gamePrice;
  private final int firstHealth;

  public int getShopPrice() {
    return shopPrice;
  }
  public int getGamePrice() {
    return gamePrice;
  }
  public int getFirstHealth() {
    return firstHealth;
  }

  public BeingDna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction, int shopPrice,
      int gamePrice, int firstHealth) {
    super(name, image, speed, leftToRight, powerOfDestruction);
    this.shopPrice = shopPrice;
    this.gamePrice = gamePrice;
    this.firstHealth = firstHealth;
  }


}