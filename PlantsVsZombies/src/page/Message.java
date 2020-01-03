package page;

import main.Program;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.Effect;
import util.Unit;

/**
 * show a message string
 */
public class Message implements Page<Unit> {

  private final String message;

  @Override
  public Effect<Unit> action() {
    return new Effect<>(h -> {
      try {
        System.out.println(message);
        Group g = new Group();
        Rectangle r = new Rectangle();
        r.setHeight(Program.screenY);
        r.setWidth(Program.screenX);
        r.setOpacity(0.5);
        Text t = new Text(message);
        t.setFont(Font.font(Program.screenY / 10));
        t.setTranslateY(Program.screenY / 2);
        g.getChildren().add(r);
        g.getChildren().add(t);
        Pane root = (Pane) Program.stage.getScene().getRoot();
        root.getChildren().add(g);
        r.setOnMouseClicked(e -> {
          root.getChildren().remove(g);
          h.success(Unit.value);
        });
      } catch (Throwable e) {
        h.failure(e);
      }
    });
  }

  /**
   * @param message The message to be shown
   */
  public Message(String message) {
    this.message = message;
  }

  public static Effect<Unit> show(String message) {
    return (new Message(message)).action();
  }
}