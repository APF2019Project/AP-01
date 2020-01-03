package graphic;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CloseButton extends Group {
  public CloseButton() {
    super();
    Circle circle = new Circle();
    circle.setFill(Color.RED);
    circle.setCenterX(40);
    circle.setCenterY(40);
    circle.setRadius(40);
    getChildren().add(circle);
    this.setOnMouseEntered(e -> {
      circle.setFill(Color.PURPLE);
    });
    this.setOnMouseExited(e -> {
      circle.setFill(Color.RED);
    });
  }
}