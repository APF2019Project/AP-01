package page;

import java.util.stream.Stream;

import main.Program;
import util.Effect;

/**
 * ask some questions from user
 */
public class Form implements Page<String[]> {

  private String[] asks;

  public Effect<String[]> action() {
    Program.clearScreen();
    String[] answers = Stream.of(asks).map(x->{
      System.out.println(x+":");
      return Program.scanner.nextLine();
    }).toArray(String[]::new);
    return Effect.ok(answers);
  }

  public Form(String ...asks) {
    this.asks = asks;
  }
}