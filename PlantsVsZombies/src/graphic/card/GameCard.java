package graphic.card;

import creature.Dna;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import main.Program;
import page.Message;

public class GameCard extends SimpleCard {

  private static double dragX, dragY;
  private static GameCard draged = null;
  private static boolean attached = false;
  public static void attachGlass() {
    if (attached) return;
    attached = true;
    Program.stage.getScene().addEventFilter(MouseEvent.MOUSE_MOVED,e -> {
      if (draged == null) return;
      draged.setTranslateX(e.getX() - dragX);
      draged.setTranslateY(e.getY() - dragY);
    });
  }

  public GameCard(Dna dna, double x, double y, double size) {
    super(dna, x, y, size);
    attachGlass();
    this.imageView.setOnMouseDragged(e -> {
      System.out.println("press\n");
      dragX = e.getX();
      dragY = e.getY();
      draged = this;
    });
    this.imageView.setOnMouseDragReleased(e->{
      System.out.println("release\n");
      //draged = null;
    });
  }

}