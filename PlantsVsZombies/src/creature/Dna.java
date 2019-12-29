package creature;

public class Dna {
  private final String name;
  private final String image;
  private final int speed;
  private final boolean leftToRight;
  private final int powerOfDestruction;

  public Dna(String name, String image, int speed, boolean leftToRight, int powerOfDestruction) {
    this.name = name;
    this.image = image;
    this.speed = speed;
    this.leftToRight = leftToRight;
    this.powerOfDestruction = powerOfDestruction;
  }

  public String getName() {
    return name;
  }

  public String getImage() {
    return image;
  }

  public int getSpeed() {
    return speed;
  }

  public boolean isLeftToRight() {
    return leftToRight;
  }

  public int getPowerOfDestruction() {
    return powerOfDestruction;
  }

    @Override
    public String toString() {
        return "Dna{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                '}';
    }
}