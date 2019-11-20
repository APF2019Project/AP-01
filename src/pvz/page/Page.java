package pvz.page;

public interface Page<U> {
  PageResult<U> action();
}