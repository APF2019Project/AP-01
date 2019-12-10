package page;

public interface Page<U> {
  PageResult<U> action();
}