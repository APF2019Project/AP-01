package page.menu;

import util.Result;
import util.Task;

public class ActionButton<U> implements Button<U> {
  private String label;
  private Task task;

  public String getLabel(){
    return label;
  }

  public Result<U> action() {
    task.run();
    return Result.error("end action");
  }

  public ActionButton(String label, Task task) {
    this.label = label;
    this.task = task;
  }

  @Override
  public String toString() {
    return "LinkButton [label=" + label + "]";
  }

  @Override
  public String getHelp() {
    return "This button will " + label;
  }
}