package game.pvp;

import account.Account;
import client.Client;
import creature.Dna;
import game.GameMode;
import graphic.GameBackground;
import graphic.game.CreatureNode;
import javafx.scene.layout.Pane;
import main.Program;
import page.Page;
import util.Effect;
import util.Unit;

public class PutPage implements Page<Unit> {

  private GameMode gameMode;
  private PvpGame game;
  private CreatureNode[][] nodes = new CreatureNode[5][7];
  private Pane pane;
  private String[][] objs;

  @Override
  public Effect<Unit> action() {
    return new Effect<>(h->{
      pane = new Pane();
      GameBackground background = new GameBackground(
          gameMode,
          Effect.noOp
      );
      pane.getChildren().add(background);
      update(game);
      Program.stage.getScene().setRoot(pane);
    });
  }

  public Effect<Unit> put(Dna dna, int i, int j) {
    return Client.get("pvp/put", Account.myToken, game.id+"",
      i+"", j+"", dna.getName()).discardData();
  }

  public void update(PvpGame newGame) {
    game = newGame;
    objs = gameMode == GameMode.DAY ? game.plants : game.zombies;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 7; j++) {
        if (objs[i][j] == null) {
          remove(i,j);
          return;
        }
        if (nodes[i][j] == null) {
          add(i,j);
          return;
        }
        if (objs[i][j].equals(nodes[i][j].getDna().getName())) {
          return;
        }
        remove(i,j);
        add(i,j);
      }
    }
  }

  private void add(int i, int j) {
    if (objs[i][j] == null) return;
    nodes[i][j] = CreatureNode.from(objs[i][j], i, j);
    pane.getChildren().add(nodes[i][j]);
  }

  private void remove(int i, int j) {
    if (nodes[i][j] == null) return;
    pane.getChildren().remove(nodes[i][j]);
  }

  public PutPage(GameMode gameMode, PvpGame game) {
    this.gameMode = gameMode;
    this.game = game;
  }

}