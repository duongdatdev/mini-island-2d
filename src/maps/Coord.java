package maps;

public class Coord {

    private final int x;
    private final int y;

    /**
     * Data structure for storing 2D coordinates.
     *
     * @param x x position
     * @param y y position
     */
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets value of x.
     *
     * @return x value of 2D coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets value of y.
     *
     * @return y value of 2D coordinate.
     */
    public int getY(){
        return y;
    }

    @Override
    /**
     * Checks whether is this object has got same coordinates.
     *
     * @param obj another object
     * @return whether is another object equal
     * @see java.lang.Object
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord other)) return false;
        return (x == other.x && y == other.y);
    }

    @Override
    /**
     * Computes hashcode of 2D coordinate.
     *
     * return hashcode of 2D coordinate
     */
    public int hashCode() {
        return 23*x+37*y;
    }
}
