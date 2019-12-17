package main;

import java.io.File;
import java.util.Scanner;

import account.Account;

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
      workingDirectory += "/Library/Application Support/PVZ2/";
    }
    return workingDirectory+file;
  }
  public static void main(String args[]) {
    scanner = new Scanner(System.in);
    File mainPath = new File(getBackupPath(""));
    if (mainPath.exists()){
      Account.recoverAll();
    }
    else {
      mainPath.mkdirs();
    }
    Pages.loginMenu.action();
    Account.backupAll();
  }
}