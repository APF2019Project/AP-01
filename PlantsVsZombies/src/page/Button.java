package page;

import util.Result;

public interface Button<U> {
  String getLabel();
  String getHelp();
  Result<U> action();
}
