package creature;

public class Location implements Comparable <Location> {

    public final int lineNumber;
    public final int position;

    public Location(int lineNumber, Integer position) {
        this.lineNumber = lineNumber;
        this.position = position;
    }

    public Location left() throws Exception {
        if (this.position == 0) throw new Exception("out of bound");
        return new Location(this.lineNumber, this.position - 1);
    }
    public boolean equals(Location location){
        return lineNumber == location.lineNumber && position == location.position;
    }

    @Override
    public int compareTo(Location location) {
        if (position == location.position) return lineNumber - location.lineNumber;
        return position - location.position;
    }
}