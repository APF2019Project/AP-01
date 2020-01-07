package page;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import creature.Dna;
import graphic.CloseButton;
import graphic.card.SimpleCard;
import javafx.scene.layout.Pane;
import main.ConsoleColors;
import main.Program;
import util.Effect;

public class Collection<U extends Dna> implements Page<ArrayList<U>> {
  private Supplier<ArrayList<U>> optionsSupplier;
  private int count;

  @Override
  public Effect<ArrayList<U>> action() {
    return new Effect<>(h -> {
      Pane myPane = new Pane();
      CloseButton closeButton = new CloseButton();
      closeButton.setOnMouseClicked(e -> {
        h.failure(new Error("form aborted"));
      });
      myPane.getChildren().add(closeButton);
      ArrayList<U> options = optionsSupplier.get();
      Random rnd = new Random();
      for (U option: options) {
        SimpleCard card = new SimpleCard(
          option,
          rnd.nextDouble()*Program.screenX,
          rnd.nextDouble()*Program.screenY,
          Program.screenX/20
        );
        myPane.getChildren().add(card);
      }
      Program.stage.getScene().setRoot(myPane);
    });
  }

  public Collection(Supplier<ArrayList<U>> optionsSupplier, int count) {
    this.optionsSupplier = optionsSupplier;
    this.count = count;
  }

}