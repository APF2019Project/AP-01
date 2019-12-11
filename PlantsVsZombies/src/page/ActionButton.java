package page;

import util.Result;
import util.Task;

public class ActionButton implements Button<Void> {
  private String label;
  private Task task;

  public String getLabel(){
    return label;
  }

  public Result<Void> action() {
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