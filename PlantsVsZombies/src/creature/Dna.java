package creature;

public class Dna {
  private String name;
  private String image;
  private int speed;
  private int powerOfDestruction;

  public String getName() {
    return name;
  }

  public String toString() {
    return name;
  }

  public String getImage() {
    return image;
  }

  public int getSpeed() {
    return speed;
  }

  public int getPowerOfDestruction() {
    return powerOfDestruction;
  }

  public Dna(String name, String image, int speed, int powerOfDestruction) {
    this.name = name;
    this.image = image;
    this.speed = speed;
    this.powerOfDestruction = powerOfDestruction;
  }
}