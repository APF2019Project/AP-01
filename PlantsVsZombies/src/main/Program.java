package main;

import account.Account;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.Effect;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public class Program extends Application {

  public static Scanner scanner;

  public static double screenX, screenY;
  public static Stage stage;

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static String getBackupPath(String file) {
    String workingDirectory;
    String OS = (System.getProperty("os.name")).toUpperCase();
    if (OS.contains("WIN")) {
      workingDirectory = System.getenv("AppData");
    } else {
      workingDirectory = System.getProperty("user.home");
      workingDirectory += "/Library/Application Support/PVZ4/";
    }
    return workingDirectory + file;
  }

  public static String streamToString(InputStream is) {
    Scanner s = new Scanner(is);
    Scanner s2 = s.useDelimiter("\\A");
    String result = s2.hasNext() ? s2.next() : "";
    s.close();
    return result;
  }

  public static void main(String[] args) {
    launch();
  }

  public static InputStream getRes(String s) {
    return Program.class.getResourceAsStream("resource/"+s);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Program.stage = stage;
    stage.widthProperty().addListener((e)-> {
      screenX = stage.getWidth();
      screenY = stage.getHeight();    
    });
    stage.heightProperty().addListener((e)-> {
      screenX = stage.getWidth();
      screenY = stage.getHeight();
      Pages.loginMenu.action()
      .discardData()
      .catchThen(e2 -> Effect.syncWork(()->{
        Account.backupAll();
        System.exit(0);
      }))
      .execute();   
    });
    Label l = new Label("Loading...");
    l.setStyle("-fx-font: 3cm arial");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setScene(scene);
    stage.setFullScreenExitHint("");
    stage.setFullScreen(true);
    stage.show();
    screenX = stage.getWidth();
    screenY = stage.getHeight();

    try {
      File mainPath = new File(getBackupPath(""));
      if (mainPath.exists()) {
        Account.recoverAll();
      } else {
        mainPath.mkdirs();
      }
      PlantDna.loadFromData(streamToString(getRes("plantDna.json")));
      ZombieDna.loadFromData(streamToString(Program.class.getResourceAsStream("resource/zombieDna.json")));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}