package page.menu;

import util.Result;
import util.Unit;

public class ExitButton implements Button<Unit> {

  private final String label;

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getHelp() {
    return "This button will "+label;
  }

  @Override
  public Result<Unit> action() {
    return Result.ok();
  }

  public ExitButton(String label) {
    this.label = label;
  }
  
}