package pvz.page;

public interface Button<U> {
  String getLabel();
  PageResult<U> action();
}