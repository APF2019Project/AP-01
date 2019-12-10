package page;

import util.Task;

public class ActionButton implements Button<Void> {
  private String label;
  private Task task;

  public String getLabel(){
    return label;
  }
  public PageResult<Void> action() {
    task.run();
    return PageResult.aborted();
  }

  public ActionButton(String label, Task task) {
    this.label = label;
    this.task = task;
  }

  @Override
  public String toString() {
    return "LinkButton [label=" + label + "]";
  }
}