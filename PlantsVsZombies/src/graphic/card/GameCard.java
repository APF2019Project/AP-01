package graphic.card;

import creature.Dna;
import creature.being.plant.PlantDna;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import main.Program;
import page.Message;
import player.plant_player.PlantPlayer;

class Delta {
  double x,y;
}

public class GameCard extends SimpleCard {

  public GameCard(PlantPlayer owner, PlantDna dna, double x, double y, double size) {
    super(dna, x, y, size);
    final Delta dragDelta = new Delta();
    Node label = this;
    label.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        // record a delta distance for the drag and drop operation.
        dragDelta.x = label.getLayoutX() - mouseEvent.getSceneX();
        dragDelta.y = label.getLayoutY() - mouseEvent.getSceneY();
        label.setCursor(Cursor.MOVE);
      }
    });
    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        label.setCursor(Cursor.HAND);
        double x = label.getTranslateX()+label.getLayoutX();
        double y = label.getTranslateY()+label.getLayoutY();
        x /= Program.screenX/9;
        y /= Program.screenY/5;
        owner.plant(dna, (int)y, (int)x);
        label.setLayoutX(0);
        label.setLayoutY(0);
      }
    });
    label.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
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