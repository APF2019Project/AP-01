package page;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;
import creature.Dna;
import graphic.CloseButton;
import graphic.SimpleButton;
import graphic.card.SelectableCard;
import javafx.scene.layout.Pane;
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
      ArrayList<U> result = new ArrayList<>();
      myPane.getChildren().add(closeButton);
      Random rnd = new Random();
      SimpleButton next = new SimpleButton(
        rnd.nextDouble()*Program.screenX,
        rnd.nextDouble()*Program.screenY,
        Program.screenX/5,
        Program.screenX/5,
        "0/"+count,
        Effect.syncWork(()->{
          h.success(result);
        })
      );
      myPane.getChildren().add(next);
      ArrayList<U> options = optionsSupplier.get();
      for (U option: options) {
        SelectableCard card = new SelectableCard(
          option,
          rnd.nextDouble()*Program.screenX,
          rnd.nextDouble()*Program.screenY,
          Program.screenX/20,
          Effect.syncWork(()->{
            if (!result.contains(option)) {
              result.add(option);
            }
            else {
              result.remove(option);
            }
            next.setText(result.size()+"/"+count);
          })
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