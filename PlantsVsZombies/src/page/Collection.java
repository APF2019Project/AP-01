package page;

import creature.Dna;
import util.Result;

public class Collection<U extends Dna> implements Page<U[]> {

    private U[] options; 

    @Override
    public Result<U[]> action() {
        Message.show("here you will choose your cards");
        return Result.ok(options);
    }

    public Collection(U[] options) {
        this.options = options;
    }

}