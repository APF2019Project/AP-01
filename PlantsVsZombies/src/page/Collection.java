package page;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import creature.Dna;
import main.ConsoleColors;
import main.Program;
import util.Effect;

public class Collection<U extends Dna> implements Page<ArrayList<U>> {
  private ArrayList<U> options; 
  private int count;

  @Override
  public Effect<ArrayList<U>> action() {
    int n = options.size();
    boolean[] selected = new boolean[n];
    while (true) {
      Program.clearScreen();
      System.out.println("Choose a number for select/deselect, or c for continue, or e for exit:");
      int choosenCount = IntStream.range(0, n).map(x -> selected[x] ? 1 : 0).sum();
      System.out.println("You choosed "+ConsoleColors.red(choosenCount+"/"+count));
      for (int i=0; i<n; i++) {
        System.out.println(i+". "+(selected[i] ? "X" : "O")+" "+options.get(i).getName());
      }
      String input = Program.scanner.nextLine();
      if (input.equals("c")) {
        if (choosenCount != count) {
          Message.show("Please choose exactly "+count+" cards");
          continue;
        }
        return Effect.ok(IntStream.range(0, options.size())
          .filter(i -> selected[i])  
          .mapToObj(i -> options.get(i))
          .collect(Collectors.toCollection(ArrayList::new)));
      }
      if (input.equals("")) continue;
      if (input.equals("e")) {
        return Effect.error("intrupted");
      }
      try {
        selected[Integer.parseInt(input)] ^= true;
      } 
      catch(NumberFormatException e) {
        Message.show("choose a valid option");
      }
      catch (ArrayIndexOutOfBoundsException e) {
        Message.show("choose a number between 0 and "+(n-1));
      }
    }
  }   

  public Collection(ArrayList<U> options, int count) {
    this.options = options;
    this.count = count;
  }

}