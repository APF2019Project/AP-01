package creature;

public class Location implements Comparable <Location> {

    public Integer lineNumber;
    public Integer position;

    public Location(Integer lineNumber, Integer position) {
        this.lineNumber = lineNumber;
        this.position = position;
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