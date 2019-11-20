package pvz.page;

import java.util.stream.Stream;

import pvz.Program;

public class Form implements Page<String[]> {

  private String[] asks;

  public PageResult<String[]> action() {
    String[] answers = Stream.of(asks).map(x->{
      System.out.println(x);
      return Program.scanner.nextLine();
    }).toArray(String[]::new);
    return new PageResult<>(false, answers);
  }

  public Form(String[] asks) {
    this.asks = asks;
  }
}