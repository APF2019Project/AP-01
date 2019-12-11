package page;

import java.util.stream.Stream;

import main.Program;
import util.Result;

public class Form implements Page<String[]> {

  private String[] asks;

  public Result<String[]> action() {
    Program.clearScreen();
    String[] answers = Stream.of(asks).map(x->{
      System.out.println(x+":");
      return Program.scanner.nextLine();
    }).toArray(String[]::new);
    return Result.ok(answers);
  }

  public Form(String ...asks) {
    this.asks = asks;
  }
}