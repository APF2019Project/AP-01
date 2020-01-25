package graphic;

import game.GameMode;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Program;
import util.Effect;
import util.Unit;

public class GameBackground extends Group {
  public GameBackground(GameMode gameMode, Effect<Unit> onClose) {
    ImageView imageView = new ImageView(new Image(
      Program.getRes("images/mode/"+gameMode+"/background.webp")
    ));
    imageView.setFitHeight(Program.screenY);
    imageView.setFitWidth(Program.screenX);
    this.getChildren().add(imageView);
    SimpleButton close = new SimpleButton(
      Program.screenX*0.8, 0, Program.screenX*0.2,
      Program.screenX*0.04, "menu", onClose);
    this.getChildren().add(close);
  }
}