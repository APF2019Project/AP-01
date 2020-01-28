package graphic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Program;

public class Wallpaper extends ImageView {
  public Wallpaper() {
    this.setImage(new Image(
      Program.getRes("images/wallpaper.jpg")
    ));
    this.setFitWidth(Program.screenX);
    this.setFitHeight(Program.screenY);
  }

  public static void addTo(Pane pane) {
    pane.getChildren().add(new Wallpaper());
  }
}