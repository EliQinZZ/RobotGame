/**
 * File Name: Direction.java
 * Description: This is the direction class used to describe the facing of the
 *              robot.
 * Author: Zhizhen (Eli) Qin
 */

public enum Direction {
    // Enumaration for North, South, West and East 
    NORTH(0, 1), SOUTH(0, -1), WEST(-1, 0), EAST(1, 0);

    private int x;  // The horizontal direction
    private int y;  // The vertical direction
    
    /**
     * The constructor of the direction
     *
     * @param x the horizontal direction
     * @param y the vertical direction
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for instance variable x
     *
     * @return the horizontal direction
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for instance variable y
     *
     * @return the vertical direction
     */
    public int getY() {
        return y;
    }

    public String toString() {
        return name() + "(" + x + ", " + y + ")";
    }
}
