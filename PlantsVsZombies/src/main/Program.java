package main;

import java.util.Scanner;

import account.Account;

public class Program {

  public static Scanner scanner;
  
  public static void clearScreen() {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  }
  public static void main(String args[]) {
    scanner = new Scanner(System.in);
    Account.create("a", "a");
    Pages.loginMenu.action();
  }
}