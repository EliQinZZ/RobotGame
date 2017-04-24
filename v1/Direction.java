//------------------------------------------------------------------//
// Direction.java                                                   //
//                                                                  //
// Enumeration Type used to represent a Movement Direction          //
//  Each Direction object includes the vector of Direction          //
//                                                                  //
// Author:  Ujjwal Gulecha		                            //
// Date:    10/12/16                                                //
//------------------------------------------------------------------//

/**
 * DO NOT MODIFY THIS FILE
 */

public enum Direction {
    // The Definitions for UP, DOWN, LEFT, and RIGHT
    NORTH(0, 1), SOUTH(0, -1), WEST(-1, 0), EAST(1, 0);

    private final int y;
    private final int x;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Retrieve the X component of direction
    public int getX() {
        return x;
    }

    // Retrieve the Y component of direction
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return name() + "(" + x + ", " + y + ")";
    }

}
