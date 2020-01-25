package graphic.game;

import creature.Creature;
import creature.Dna;
import creature.ammunition.Ammunition;
import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import game.GameEngine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Program;

public class CreatureNode extends ImageView {

  Creature creature;
  Dna dna;
  double size;

  static double marginY = Program.screenY;

  public CreatureNode(Creature creature) {
    size = Program.screenY / 8;
    if (creature instanceof Plant) {
      dna = ((Plant)creature).getPlantDna();
    }
    if (creature instanceof Zombie) {
      dna = ((Zombie)creature).getZombieDna();
      size *= 1.6;
    }
    if (creature instanceof Ammunition) {
      dna = ((Ammunition)creature).getAmmunitionDna();
      size /= 2.6;
    }
    try{
      this.setImage(new Image(
        Program.getRes(dna.getGameImageUrl())
      ));
    }
    catch(Throwable e) {
      this.setImage(new Image(
        Program.getRes("images/lanat.png")
      ));
    }
    this.creature = creature;
    this.setFitHeight(size);
    this.setFitWidth(size);
    this.update();
  }

  public void update() {
    this.setX(GameEngine.getCurrentGameEngine().getGraphicalX(creature));
    this.setY(GameEngine.getCurrentGameEngine().getGraphicalY(creature));
  }
}