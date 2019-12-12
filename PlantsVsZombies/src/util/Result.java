package util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import page.Message;

/**
 * Result<T> can contain a data of type T in case of success and
 * a error message in case of failure
 * @param <T> type of inside data
 */
public class Result<T> {
  private Optional<T> value;
  private Optional<String> error;

  private Result(T value, String error) {
    this.value = Optional.ofNullable(value);
    this.error = Optional.ofNullable(error);
  }

  /**
   * @param <U> type of value
   * @param value 
   * @return a Result wrapper that contain value
   */
  public static <U> Result<U> ok(U value) {
    return new Result<>(value, null);
  }

  public static Result<Unit> ok() {
    return Result.ok(Unit.value);
  }

  /**
   * @param <U>
   * @param error the message of error
   * @return a result value failed with error
   */
  public static <U> Result<U> error(String error) {
    return new Result<>(null, error);
  }

  /**
   * Check this result is failed or not
   * @return true when fail, false when success
   */
  public boolean isError() {
    return error.isPresent();
  }

  /**
   * @return value inside Result object
   * @throws NoSuchElementException in case of failure
   */
  public T getValue() {
    return value.get();
  }

  /**
   * @return error message
   * @throws NoSuchElementException in case of success
   */
  public String getError() {
    return error.get();
  }

  /**
   * chain results and fails with first error
   * @param <U>
   * @param mapper a function that transform this value to another result
   * @return combined result
   */
  public <U> Result<U> flatMap(Function<T, Result<U>> mapper) {

    if (this.isError()) {
      return Result.error(this.getError());
    }

    return mapper.apply(value.get());
  }

  /**
   * transform inside data with a function in case of success
   * @param <U>
   * @param mapper a function that transform this value to another value
   * @return transformed result
   */
  public <U> Result<U> map(Function<T, U> mapper) {

    if (this.isError()) {
      return Result.error(this.getError());
    }

    return Result.ok(mapper.apply(value.get()));
  }

  public Result<Unit> discardData() {
    return this.map(x -> Unit.value);
  }

  public Result<Unit> consume(Consumer<T> consumer) {
    return this.map(x -> {
      consumer.accept(x);
      return Unit.value;
    });
  }

  /**
   * show contained value in a {@link Message} page in case of success
   * @return Result of operation
   */
  public Result<Unit> show() {
    return this.consume(x -> new Message(x.toString()).action());
  }

  /**
   * show error in a {@link Message} page in case of error
   * @return this
   */
  public Result<T> showError() {
    if (isError()) new Message("Error: " + getError()).action();
    return this;
  }
}