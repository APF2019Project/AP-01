package pvz;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import pvz.page.ActionButton;
import pvz.page.Button;
import pvz.page.Form;
import pvz.page.LinkButton;
import pvz.page.Menu;
import pvz.page.Page;
import pvz.page.PageResult;

public class Program {

  public static Scanner scanner;
  public static <U> Page<U> notImplemented(){
    return new Page<U>(){
      @Override
      public PageResult<U> action() {
        System.out.println("ishalla in future");
        return PageResult.aborted();
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
        createAccountForm.action();
      }),
      new LinkButton<Void>("login", mainMenu),
      new LinkButton<Void>("leaderboard", notImplemented())
    );
    loginMenu.action();
  }
}