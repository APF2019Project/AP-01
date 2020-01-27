package graphic;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Program;

public class CloseButton extends Group {
  public CloseButton() {
    super();
    Circle circle = new Circle();
    circle.setFill(Color.RED);
    circle.setCenterX(Program.screenX / 200);
    circle.setCenterY(Program.screenX / 200);
    circle.setRadius(Program.screenX / 200);
    getChildren().add(circle);
    this.setOnMouseEntered(e -> {
      circle.setFill(Color.PURPLE);
    });
    this.setOnMouseExited(e -> {
      circle.setFill(Color.RED);
    });
  }
}