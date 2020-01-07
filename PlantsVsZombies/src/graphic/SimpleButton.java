package graphic;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import util.Effect;
import util.Unit;

public class SimpleButton extends Group {
  private Button button;

  public SimpleButton(double positionX, double positionY, double sizeX, double sizeY, String label,
      Effect<Unit> action) {
        super();
        this.setTranslateX(positionX);
        this.setTranslateY(positionY);
        button = new Button(label);
        button.setPrefSize(sizeX, sizeY);
        button.setFont(Font.font(sizeY/3));
        button.setOnAction(e -> {
          action.execute();
        });
        this.getChildren().add(button);
  }

  public void setText(String string) {
    button.setText(string);
  }
}