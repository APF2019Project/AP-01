package page;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import creature.Dna;
import main.ConsoleColors;
import main.Program;
import util.Effect;

public class Collection<U extends Dna> implements Page<ArrayList<U>> {
  private Supplier<ArrayList<U>> optionsSupplier;
  private ArrayList<U> options; 
  private int count;

  @Override
  public Effect<ArrayList<U>> action() {
    return Effect.noOp.map(x->null);
  }

  public Collection(Supplier<ArrayList<U>> optionsSupplier, int count) {
    this.optionsSupplier = optionsSupplier;
    this.count = count;
  }

}