package graphic.card;

import creature.Dna;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Program;

public class SimpleCard extends Group {
  
  protected Rectangle rectangle, progress;
  protected ImageView imageView;
  protected double size;

  public void setColor(Color c) {
    progress.setHeight(0);
    rectangle.setFill(c);
  }

  public void setColor(Color cUp, Color cDown, double percent) {
    progress.setHeight(size*1.3*percent);
    rectangle.setFill(cDown);
    progress.setFill(cUp);
  }

  public SimpleCard(Dna dna, double x, double y, double size) {
    super();
    this.size = size;
    this.setTranslateX(x);
    this.setTranslateY(y);
    rectangle = new Rectangle();
    rectangle.setWidth(size);
    rectangle.setHeight(size*1.3);
    progress = new Rectangle();
    progress.setWidth(size);
    progress.setHeight(0);
    this.getChildren().add(rectangle);
    this.getChildren().add(progress);
    Text tName = new Text();
    tName.setText(dna.getName());
    tName.setX(0);
    tName.setY(size/10);
    tName.setFont(Font.font(size/10));
    this.getChildren().add(tName);
    try {
      double imageMargin = size * 0.03;
      imageView = new ImageView(
        new Image(
          Program.getRes(dna.getCardImageUrl())
        )
      );
      imageView.setFitHeight(size-2*imageMargin);
      imageView.setFitWidth(size-2*imageMargin);
      imageView.setY(size/10+imageMargin);
      imageView.setX(imageMargin);
      this.getChildren().add(imageView);
    }
    catch (Throwable e) {
      System.out.println(e.getMessage());
      System.out.println(dna.getCardImageUrl());
    }
  }
}