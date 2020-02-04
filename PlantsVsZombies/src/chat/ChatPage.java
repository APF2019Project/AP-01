package chat;

import main.Program;
import page.Page;
import graphic.CloseButton;
import graphic.SimpleButton;
import account.Account;
import client.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.Effect;
import util.Unit;

public class ChatPage implements Page<Void> {

  private VBox vBox;
  private ScrollPane sPane;
  private final String username;

  public Effect<Unit> reload() {
    return Client.get("getChat", Account.myToken+"\n"+username+"\n")
    .map(data->data.split("\n"))
    .flatMap(data -> Effect.syncWork(()->{
      vBox = new VBox();
      for (int i=0; i<data.length/2; i++) {
        Text text = new Text(data[2*i]+": "+data[2*i+1]);
        text.setFont(Font.font(Program.screenY/20));
        vBox.getChildren().add(text);
      }
      sPane.setContent(vBox);
    }));
  }

  @Override
  public Effect<Void> action() {
    return new Effect<Void>(h->{
        Pane pane = new Pane();
        CloseButton closeButton = new CloseButton();
        closeButton.setOnMouseClicked(e -> {
          h.failure(new Error("closed"));
        });
        sPane = new ScrollPane();
        sPane.setTranslateX(Program.screenX/4);
        sPane.setPrefWidth(Program.screenX/2);
        sPane.setPrefHeight(Program.screenY*0.9);
        reload().execute();
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
          reload().execute();
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        pane.getChildren().add(closeButton);
        pane.getChildren().add(sPane);
        TextField textField = new TextField();
        textField.setTranslateY(Program.screenY*0.9);
        textField.setPrefWidth(Program.screenX*0.9);
        textField.setPrefHeight(Program.screenY*0.1);
        textField.setFont(Font.font(Program.screenY*0.05));
        SimpleButton simpleButton = new SimpleButton(
          Program.screenX*0.9,
          Program.screenY*0.9,
          Program.screenX*0.1,
          Program.screenY*0.1,
          "send",
          (Effect.fromSync(()->{
            String s = textField.getText();
            textField.setText("");
            return s;
          })).flatMap(data -> Client.get(
            "sendMessage",
            Account.myToken+"\n"+username+"\n"+data+"\n"
          )
          .then(reload()))
        );
        pane.getChildren().add(textField);
        pane.getChildren().add(simpleButton);
        Program.stage.getScene().setRoot(pane);
      });  
  }

  public ChatPage(String username) {
    this.username = username;
  }

}