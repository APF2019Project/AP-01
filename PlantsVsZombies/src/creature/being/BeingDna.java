package creature.being;

import creature.Dna;
import game.GameEngine;
import line.LineState;

public class BeingDna extends Dna {

  private final int shopPrice;
  private final int gamePrice;
  private final int firstHealth;
  private final LineState lineState;

  public int getShopPrice() {
    return shopPrice;
  }
  public int getGamePrice() {
    return gamePrice;
  }
  public int getFirstHealth() {
    return firstHealth;
  }

  public BeingDna(String name, int speed, boolean leftToRight, int powerOfDestruction, int shopPrice,
      int gamePrice, int firstHealth, LineState lineState) {
    super(name, speed, powerOfDestruction);
    this.shopPrice = shopPrice;
    this.gamePrice = gamePrice;
    this.firstHealth = firstHealth * GameEngine.getFRAME();
    this.lineState = lineState;
  }

  public LineState getLineState() {
    return lineState;
  }


}