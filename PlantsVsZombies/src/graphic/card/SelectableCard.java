package graphic.card;

import creature.Dna;
import javafx.scene.paint.Color;

public class SelectableCard extends SimpleCard {

  private boolean selected = false;

  public SelectableCard(Dna dna, double x, double y, double size) {
    super(dna, x, y, size);
    recolor();
    this.setOnMouseEntered(e -> {
      rectangle.setFill(Color.CADETBLUE);
    });
    this.setOnMouseExited(e -> {
      recolor();
    });
    this.setOnMouseClicked(e -> {
      selected ^= true;
    });
  }

  private void recolor() {
    if (selected) this.rectangle.setFill(Color.LIGHTGREEN);
    else rectangle.setFill(Color.AQUA);
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

}