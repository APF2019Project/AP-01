package main;

import java.util.Scanner;

public class Program {

  public static Scanner scanner;
  
  public static void main(String args[]) {
    scanner = new Scanner(System.in);
    Pages.loginMenu.action();
  }
}