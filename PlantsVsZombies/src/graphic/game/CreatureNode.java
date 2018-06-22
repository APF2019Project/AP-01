package graphic.game;

import creature.Creature;
import creature.Dna;
import creature.ammunition.Ammunition;
import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import game.GameEngine;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Program;

public class CreatureNode extends Group {

  Creature creature;
  Dna dna;
  double size;
  private ImageView image;
  private Rectangle health1, health2;

  static double marginY = Program.screenY;

  public CreatureNode(Creature creature) {
    image = new ImageView();
    health1 = new Rectangle();
    health2 = new Rectangle();
    size = Program.screenY / 8;
    health1.setWidth(size);
    health1.setHeight(size * 0.1);
    health1.setFill(Color.RED);
    health2.setFill(Color.GREEN);
    health2.setHeight(size * 0.1);
    if (creature instanceof Plant) {
      dna = ((Plant) creature).getPlantDna();
    }
    if (creature instanceof Zombie) {
      dna = ((Zombie) creature).getZombieDna();
      size *= 1.6;
    }
    if (creature instanceof Ammunition) {
      dna = ((Ammunition) creature).getAmmunitionDna();
      size /= 2.6;
    }
    try {
      image.setImage(new Image(Program.getRes(dna.getGameImageUrl())));
    } catch (Throwable e) {
      image.setImage(new Image(Program.getRes("images/lanat.png")));
    }
    this.creature = creature;
    image.setFitHeight(size);
    image.setFitWidth(size);
    this.getChildren().add(image);
    if (!(creature instanceof Ammunition)) {
      this.getChildren().add(health1);
      this.getChildren().add(health2);
    }
    this.update();
  }

  public void update() {
    if ((creature instanceof Zombie) || (creature instanceof Ammunition)) {
      this.setTranslateX(GameEngine.getCurrentGameEngine().getGraphicalX(creature) + GameEngine.getFRAME() / 3);
      this.setTranslateY(GameEngine.getCurrentGameEngine().getGraphicalY(creature));
    } else {
      this.setTranslateX(GameEngine.getCurrentGameEngine().getGraphicalX(creature));
      this.setTranslateY(GameEngine.getCurrentGameEngine().getGraphicalY(creature));
    }
    if (!(creature instanceof Ammunition)) {
      health2.setWidth(creature.getHealth()/GameEngine.getFRAME());
    }
  }
}