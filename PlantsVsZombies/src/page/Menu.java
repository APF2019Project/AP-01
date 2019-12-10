package page;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.Program;

public class Menu<U> implements Page<U> {

  private final ArrayList<Button<U>> buttons;

  public Menu(final ArrayList<Button<U>> buttons) {
    this.buttons = buttons;
  }

  public Menu(final Button<U>... buttons) {
    this.buttons = Stream.of(buttons).collect(Collectors.toCollection(ArrayList::new));
  }

  public PageResult<U> action() {
    for (int i = 0; i < buttons.size(); i++) {
      System.out.println(i + ". " + buttons.get(i).getLabel());
    }
    final String s = Program.scanner.nextLine();
    final int k = Integer.valueOf(s);
    if (k < 0 || k >= buttons.size())
      return PageResult.aborted();
    final PageResult<U> result = buttons.get(k).action();
    if (result.isAborted) return action();
    else return result;
  }

  @Override
  public String toString() {
    return "Menu [buttons=" + buttons + "]";
  }
}