package main;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import account.Account;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;

public class Program {

  public static Scanner scanner;
  
  public static void clearScreen() {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  }

  public static String getBackupPath(String file) {
    String workingDirectory;
    String OS = (System.getProperty("os.name")).toUpperCase();
    if (OS.contains("WIN"))
    {
      workingDirectory = System.getenv("AppData");
    }
    else
    {
      workingDirectory = System.getProperty("user.home");
      workingDirectory += "/Library/Application Support/PVZ4/";
    }
    return workingDirectory+file;
  }

  public static String streamToString(InputStream is) {
    Scanner s = new Scanner(is);
    Scanner s2 = s.useDelimiter("\\A");
    String result = s2.hasNext() ? s2.next() : "";
    s.close();
    return result;
  }

  public static void main(String args[]) {
    try{
      scanner = new Scanner(System.in);
      File mainPath = new File(getBackupPath(""));
      if (mainPath.exists()){
        Account.recoverAll();
      }
      else {
        mainPath.mkdirs();
      }
      PlantDna.loadFromData(
        streamToString(
          Program.class.getResourceAsStream("resource/plantDna.json")
        )
      );
      ZombieDna.loadFromData(
        streamToString(
          Program.class.getResourceAsStream("resource/zombieDna.json")
        )
      );
      Pages.loginMenu.action();
      Account.backupAll();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}