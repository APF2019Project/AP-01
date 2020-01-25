package graphic.card;

import creature.Dna;
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

class Delta {
  double x,y;
}

public class GameCard extends SimpleCard {

  public GameCard(Dna dna, double x, double y, double size) {
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
        label.setLayoutX(x);
        label.setLayoutY(y);
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