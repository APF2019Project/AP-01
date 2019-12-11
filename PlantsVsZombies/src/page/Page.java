package page;

import util.Result;

public interface Page<U> {
  Result<U> action();
}