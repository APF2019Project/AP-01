package page;

import util.Result;

public interface Button<U> {
  String getLabel();
  Result<U> action();
}
