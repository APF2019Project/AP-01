package game.pvp;

import client.Client;
import graphic.CloseButton;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Program;
import page.Page;
import util.Effect;
import util.Unit;

import java.util.Arrays;

public class listMenu implements Page<Unit> {
    @Override
    public Effect<Unit> action() {
        return Client.get("pvp/list", "").map(x -> x.split("\n")).
                map(Arrays::asList).map(list -> list.size() > 6 ? list : list.subList(6, list.size())).
                flatMap(list -> new Effect<>(h -> {
                    javafx.scene.image.Image image = new Image(Program.getRes("images/wallpaper.jpg"));
                    Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false)));

                    final double X = Program.screenX;
                    final double Y = Program.screenY;

                    VBox vBox = new VBox();
                    vBox.setFillWidth(true);
                    vBox.setAlignment(Pos.TOP_CENTER);

                    CloseButton closeButton = new CloseButton();
                    closeButton.setOnMouseClicked(e -> h.failure(new Error("end menu")));
                    HBox hBox = new HBox();
                    hBox.getChildren().add(closeButton);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    vBox.getChildren().add(hBox);
                    vBox.getChildren().add(PvpGame.packet(null, null, null, null));
                    System.out.println("DONE !!!!!!!!!!!");
                    for (String game : list) {
                        String[] res = game.split("\t");
                        vBox.getChildren().add(PvpGame.packet(res[0], res[1], res[2], res[3]));
                    }

                    Rectangle rec = new Rectangle();
                    rec.setHeight(Y);
                    rec.setWidth(X * 0.7);
                    rec.setX((X - rec.getWidth()) / 2);
                    rec.setFill(Color.rgb(0, 0, 0, 0.9));

                    StackPane stackPane = new StackPane();

                    stackPane.setBackground(background);
                    stackPane.getChildren().addAll(rec, vBox);

                    vBox.setSpacing(Y / 50);

                    Program.stage.getScene().setRoot(stackPane);

                })).showError();
    }
}
