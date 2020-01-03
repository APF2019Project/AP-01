package util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import page.Message;

/**
 * Result can contain a data of type T in case of success and
 * a error message in case of failure
 * @param <T> type of inside data
 */
public class Effect<T> {
  private Optional<T> value;
  private Optional<String> error;
  private Effect(T value, String error) {
    this.value = Optional.ofNullable(value);
    this.error = Optional.ofNullable(error);
  }
  @Override
  public String toString() {
    if (isError()) return "Error: " + getError();
    return value.toString();
  }

  /**
   * @param <U> type of value
   * @param value 
   * @return a Result wrapper that contain value
   */
  public static <U> Effect<U> ok(U value) {
    return new Effect<>(value, null);
  }

  public static Effect<Unit> ok() {
    return Effect.ok(Unit.value);
  }

  /**
   * @param <U>
   * @param error the message of error
   * @return a result value failed with error
   */
  public static <U> Effect<U> error(String error) {
    return new Effect<>(null, error);
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
  public <U> Effect<U> flatMap(Function<T, Effect<U>> mapper) {

    if (this.isError()) {
      return Effect.error(this.getError());
    }

    return mapper.apply(value.get());
  }

  /**
   * transform inside data with a function in case of success
   * @param <U>
   * @param mapper a function that transform this value to another value
   * @return transformed result
   */
  public <U> Effect<U> map(Function<T, U> mapper) {

    if (this.isError()) {
      return Effect.error(this.getError());
    }

    return Effect.ok(mapper.apply(value.get()));
  }

  public Effect<Unit> discardData() {
    return this.map(x -> Unit.value);
  }

  public Effect<Unit> consume(Consumer<T> consumer) {
    return this.map(x -> {
      consumer.accept(x);
      return Unit.value;
    });
  }

  /**
   * show contained value in a {@link Message} page in case of success
   * @return Result of operation
   */
  public Effect<Unit> show() {
    return this.consume(x -> new Message(x.toString()).action());
  }

  /**
   * show error in a {@link Message} page in case of error
   * @return this
   */
  public Effect<T> showError() {
    if (isError()) new Message("Error: " + getError()).action();
    return this;
  }

  /**
   * lift a supplier with Result monad
   * @param <U>
   * @param supplier
   * @return lifted supplier
   */
  public static <U> Supplier<Effect<U>> liftSupplier(Supplier<U> supplier) {
    return () -> Effect.ok(supplier.get());
  }
}