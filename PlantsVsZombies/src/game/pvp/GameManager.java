package game.pvp;

import client.Client;
import game.GameMode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.Program;
import page.Message;
import page.Page;
import util.Effect;
import util.Serial;
import util.Unit;

public class GameManager {

  static String id;
  static PvpGame game;
  static GameState state = null;

  static class WaitingPage implements Page<Unit> {

    @Override
    public Effect<Unit> action() {
      return new Effect<>(h-> {
        Label l = new Label("Wait for other player...");
        l.setStyle("-fx-font: 3cm arial");
        Program.stage.getScene().setRoot(new StackPane(l));
      });
    }

  }

  static PutPage putPage;

  public static Effect<Unit> reload(){
    return Client.get("pvp/get", id+"\n")
      .map(x -> (PvpGame)Serial.fromBase64(x))
      .flatMap(newGame -> Effect.syncWork(()->{
        game = newGame;
      }))
      .then(Effect.syncWork(()->{
        if (!game.gameState.equals(state)) {
          System.out.println("a");
          state = game.gameState;
          if (game.gameState == GameState.WAITING_TO_JOIN) {
            new WaitingPage().action().execute();
            return;
          }
          if (game.gameState == GameState.RUNNING_PLANTS ) {
            putPage = new PutPage(GameMode.DAY, game);
            putPage.action().execute();
            return;
          }
          if (game.gameState == GameState.RUNNING_ZOMBIE ) {
            new PutPage(GameMode.ZOMBIE, game).action().execute();
            return;
          }
        }
        else {
          System.out.println("b");
          if (game.gameState == GameState.RUNNING_PLANTS ) {
            putPage.update(game);
          }
          if (game.gameState == GameState.RUNNING_ZOMBIE ) {
            putPage.update(game);
          }
        }
      }));
  }

  public static Effect<Unit> init(String myId) {
    id = myId;
    return reload().then(Effect.syncWork(()->{
      Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
        reload().execute();
      }));
      fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
      fiveSecondsWonder.play();
    }));
  }
  
}