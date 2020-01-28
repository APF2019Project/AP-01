package graphic.card;

import creature.being.plant.PlantDna;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import main.Program;
import player.plant_player.PlantPlayer;

class Delta {
  double x,y;
}

public class GameCard extends SimpleCard {

  public boolean enabled = true;
  private boolean dragged = false;

  public GameCard(PlantPlayer owner, PlantDna dna, double x, double y, double size) {
    super(dna, x, y, size, dna.getGamePrice() + "");
    final Delta dragDelta = new Delta();
    Node label = this;
    label.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        // record a delta distance for the drag and drop operation.
        if (!enabled) return;
        dragged = true;
        dragDelta.x = label.getLayoutX() - mouseEvent.getSceneX();
        dragDelta.y = label.getLayoutY() - mouseEvent.getSceneY();
        label.setCursor(Cursor.MOVE);
      }
    });
    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        if (!dragged) return;
        dragged = false;
        label.setCursor(Cursor.HAND);
        double x = label.getTranslateX()+label.getLayoutX();
        double y = label.getTranslateY()+label.getLayoutY();
        x /= Program.screenX / 10;
        y /= Program.screenY/5;
        owner.plant(dna, (int)y, (int)x);
        label.setLayoutX(0);
        label.setLayoutY(0);
      }
    });
    label.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        if (!dragged) return;
        label.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
        label.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
      }
    });
    label.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        label.setCursor(Cursor.HAND);
      }
    });
  }

}