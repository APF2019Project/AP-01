package page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import creature.Dna;
import main.ConsoleColors;
import main.Program;
import util.Result;

public class Collection<U extends Dna> implements Page<ArrayList<U>> {
  private ArrayList<U> options; 
  private int count;

  @Override
  public Result<ArrayList<U>> action() {
    int n = options.size();
    boolean[] selected = new boolean[n];
    while (true) {
      Program.clearScreen();
      System.out.println("Choose a number for select/deselect, or c for continue, or e for exit:");
      int choosenCount = IntStream.range(0, n).map(x -> selected[x] ? 1 : 0).sum();
      System.out.println("You choosed "+ConsoleColors.red(choosenCount+"/"+count));
      for (int i=0; i<n; i++) {
        System.out.println(i+". "+(selected[i] ? "X" : "O")+" "+options.get(i).name);
      }
      String input = Program.scanner.nextLine();
      if (input.equals("c")) {
        return Result.ok(IntStream.range(0, options.size())
          .filter(i -> selected[i])  
          .mapToObj(i -> options.get(i))
          .collect(Collectors.toCollection(ArrayList::new)));
      }
      if (input.equals("e")) {
        return Result.error("intrupted");
      }
      selected[Integer.parseInt(input)] ^= true;
    }
  }   

  public Collection(ArrayList<U> options, int count) {
    this.options = options;
    this.count = count;
  }

}