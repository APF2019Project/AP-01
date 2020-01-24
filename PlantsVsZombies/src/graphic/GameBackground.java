package graphic;

import game.GameMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Program;

public class GameBackground extends ImageView {
  public GameBackground(GameMode gameMode) {
    super(new Image(
      Program.getRes("images/mode/"+gameMode+"/background.webp")
    ));
    this.setFitHeight(Program.screenY);
    this.setFitWidth(Program.screenX);
  }
}