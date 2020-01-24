package graphic.game;

import creature.Creature;
import creature.Dna;
import creature.being.plant.Plant;
import creature.being.zombie.Zombie;
import game.GameEngine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.Program;

public class CreatureNode extends ImageView {

  Creature creature;
  Dna dna;

  public CreatureNode(Creature creature) {
    if (creature instanceof Plant) {
      dna = ((Plant)creature).getPlantDna();
    }
    if (creature instanceof Zombie) {
      dna = ((Zombie)creature).getZombieDna();
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
    this.setFitHeight(100);
    this.setFitWidth(100);
    this.update();
  }

  public void update() {
    this.setX(GameEngine.getCurrentGameEngine().getGraphicalX(creature));
    this.setY(GameEngine.getCurrentGameEngine().getGraphicalY(creature));
  }
}