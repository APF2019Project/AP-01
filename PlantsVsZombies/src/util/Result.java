package util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T> {
  private Optional<T> value;
  private Optional<String> error;

  private Result(T value, String error) {
    this.value = Optional.ofNullable(value);
    this.error = Optional.ofNullable(error);
  }

  public static <U> Result<U> ok(U value) {
    return new Result<>(value, null);
  }

  public static Result<Unit> ok() {
    return Result.ok(Unit.value);
  }

  public static <U> Result<U> error(String error) {
    return new Result<>(null, error);
  }

  public boolean isError() {
    return error.isPresent();
  }

  public T getValue() {
    return value.get();
  }

  public String getError() {
    return error.get();
  }

  public <U> Result<U> flatMap(Function<T, Result<U>> mapper) {

    if (this.isError()) {
      return Result.error(this.getError());
    }

    return mapper.apply(value.get());
  }

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

  public Result<Unit> print() {
    return this.consume(x -> {
      if (!x.equals(""))
        System.out.println(x);
    });
  }

  public Result<T> printError() {
    if (isError()) System.out.println("Error: " + getError());
    return this;
  }
}