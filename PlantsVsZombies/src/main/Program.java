package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import account.Account;
import page.ActionButton;
import page.Button;
import page.Form;
import page.LinkButton;
import page.Menu;
import page.Page;
import util.Result;

public class Program {

  public static Scanner scanner;
  public static <U> Page<U> notImplemented(){
    return new Page<U>(){
      @Override
      public Result<U> action() {
        System.out.println("ishalla in future");
        return Result.error("end");
      }
    };
  }
  public static void main(String args[]) {
    scanner = new Scanner(System.in);
    Menu<Void> mainMenu = new Menu<Void>(
      new LinkButton<Void>("play", notImplemented()),
      new LinkButton<Void>("profile", notImplemented()),
      new LinkButton<Void>("shop", notImplemented())
    );
    Form createAccountForm = new Form(
      new String[]{ "Enter new username:", "Enter password:" }
    );
    Menu<Void> loginMenu = new Menu<Void>(
      new ActionButton("create account", ()->{
        createAccountForm.action()
        .flatMap(data -> Account.create(data[0], data[1]))
        .map((x) -> "Account created successfully")
        .print()
        .printError();
      }),
      new LinkButton<Void>("login", mainMenu),
      new LinkButton<Void>("leaderboard", notImplemented())
    );
    loginMenu.action();
  }
}