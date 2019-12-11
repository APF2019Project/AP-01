package page;

import util.Result;

public class LinkButton<U> implements Button<U> {
  private String label;
  private Page<U> page;

  public String getLabel(){
    return label;
  }
  public Result<U> action() {
    return page.action();
  }

  public LinkButton(String label, Page<U> page) {
    this.label = label;
    this.page = page;
  }

  @Override
  public String toString() {
    return "LinkButton [label=" + label + ", page=" + page + "]";
  }
}