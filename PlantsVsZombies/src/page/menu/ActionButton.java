package page.menu;

import util.Effect;
import util.Task;

public class ActionButton<U> implements Button<U> {
  private String label;
  private Task task;

  public String getLabel(){
    return label;
  }

  public Effect<U> action() {
    task.run();
    return Effect.error("end action");
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