package main;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import account.Account;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import util.Effect;

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

  public static void main(String args[]) {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {
    Label l = new Label("Loading...");
    l.setStyle("-fx-font: 3cm arial");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setScene(scene);
    stage.setFullScreenExitHint("");
    stage.setFullScreen(true);
    stage.show();
    Screen screen = Screen.getPrimary();
    screenX = screen.getVisualBounds().getWidth();
    screenY = screen.getVisualBounds().getHeight();
    Program.stage = stage;
    try {
      File mainPath = new File(getBackupPath(""));
      if (mainPath.exists()) {
        Account.recoverAll();
      } else {
        mainPath.mkdirs();
      }
      PlantDna.loadFromData(streamToString(Program.class.getResourceAsStream("resource/plantDna.json")));
      ZombieDna.loadFromData(streamToString(Program.class.getResourceAsStream("resource/zombieDna.json")));
      Pages.loginMenu.action()
      .discardData()
      .catchThen(e -> Effect.syncWork(()->{
        Account.backupAll();
        System.exit(0);
      }))
      .execute();
      l.setText("loaded");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}