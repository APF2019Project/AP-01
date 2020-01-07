package graphic.card;

import creature.Dna;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SimpleCard extends Group {
  
  public SimpleCard(Dna dna, double x, double y, double size) {
    super();
    this.setTranslateX(x);
    this.setTranslateY(y);
    Rectangle r = new Rectangle();
    r.setWidth(size);
    r.setHeight(size*1.3);
    r.setFill(Color.AQUA);
    Text tName = new Text();
    tName.setText(dna.getName());
    tName.setX(0);
    tName.setY(size/10);
    tName.setFont(Font.font(size/10));
    this.getChildren().add(r);
    this.getChildren().add(tName);
  }
}