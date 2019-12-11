package page;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.Program;
import util.Result;

public class Menu<U> implements Page<U> {

  private final ArrayList<Button<U>> buttons;

  public Menu(final ArrayList<Button<U>> buttons) {
    this.buttons = buttons;
  }

  @SafeVarargs
  public Menu(final Button<U>... buttons) {
    this.buttons = Stream.of(buttons).collect(Collectors.toCollection(ArrayList::new));
  }

  public Result<U> action() {
    Program.clearScreen();
    System.out.println("Choose an option, or e for exit and h for help");
    for (int i = 0; i < buttons.size(); i++) {
      System.out.println(i + ". " + buttons.get(i).getLabel());
    }
    final String s = Program.scanner.nextLine();
    if (s.equals("e") || s.equals("exit")) return Result.error("exited");
    if (s.equals("h") || s.equals("help")) {
      printHelp();
      return action();
    }
    final int k = Integer.valueOf(s);
    if (k < 0 || k >= buttons.size())
      return Result.error("bad param");
    final Result<U> result = buttons.get(k).action();
    if (result.isError()) return action();
    else return result;
  }

  private void printHelp() {
    for (int i = 0; i < buttons.size(); i++) {
      System.out.println(i + ". " + buttons.get(i).getLabel());
      System.out.println("    " + buttons.get(i).getHelp());
    }
  }

  @Override
  public String toString() {
    return "Menu [buttons=" + buttons + "]";
  }
}