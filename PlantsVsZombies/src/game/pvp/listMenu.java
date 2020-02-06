package game.pvp;

import client.Client;
import graphic.CloseButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Program;
import page.Page;
import util.Effect;
import util.Unit;

import java.util.Arrays;
import java.util.List;

public class listMenu implements Page<Unit> {
    @Override
    public Effect<Unit> action() {
        return Client.get("pvp/list", "").map(x -> x.split("\n")).
                map(Arrays::asList).flatMap(list -> new Effect<>(h -> {
            Image image = new Image(Program.getRes("images/wallpaper.jpg"));
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


            HBox newGameBox = new HBox();
            newGameBox.setAlignment(Pos.CENTER);
            Button newGameBtn = new Button("new game");
            newGameBtn.setTextAlignment(TextAlignment.CENTER);
            newGameBtn.setStyle(
                    "-fx-background-color: rgba(0 , 255 , 0 , 0.2);"
            );
            newGameBtn.setTextFill(Color.WHITESMOKE);
            newGameBtn.setFont(Font.font("monospaced", Program.screenX / 60));
            newGameBox.getChildren().add(newGameBtn);
            vBox.getChildren().addAll(hBox, newGameBox);

            vBox.getChildren().add(PvpGame.packet(null, null, null, null));
            List<String> newlist = list.subList(0, list.size() - 1);
            if (list.size() > 7)
                newlist = list.subList(list.size() - 7, list.size() - 1);
            for (String game : newlist) {
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
