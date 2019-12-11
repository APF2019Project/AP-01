package page;

import util.Result;
import util.Unit;

public class Help implements Page<Unit> {

  private final String text;

  @Override
  public Result<Unit> action() {
    return new Message(text).action();
  }

  public Help(String text) {
    this.text = text;
  }

}