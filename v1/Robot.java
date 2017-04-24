/**
 * File Name: Robot.java
 * Description: This is the robot class that manage the robot object.
 * Author: Zhizhen (Eli) Qin
 */

class Robot {
	private int x;									// x coordinate
	private int y;									// y coordinate
	private Direction direction;		// facing direction

	private static final int BOARD_SIZE = 8;

	/**
	 * Constructor of robot object
	 *
	 * @param x the x coordinate of the robot
	 * @param y the y coordinate of the robot
	 * @param direction the facing direction of the robot
	 */
	public Robot(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	/**
	 * Setter for instance variable x
	 *
	 * @param x the x coordinate to be set
	 */
	private void setX(int x) { this.x = x; }
	
	/**
	 * Setter for instance variable y
	 *
	 * @param y the y coordinate to be set
	 */
	private void setY(int y) { this.y = y; }

	/**
	 * Setter for instance variable direction 
	 *
	 * @param direction the direction to be set
	 */
	private void setDirection(Direction direction) {this.direction = direction; }

	/**
	 * Getter for instance variable x 
	 *
	 * @return the x coordinate of the robot
	 */
	public int getX() { return x; }

	/**
	 * Getter for instance variable y 
	 *
	 * @return the y coordinate of the robot
	 */
	public int getY() { return y; }

	/**
	 * Getter for instance variable direction 
	 *
	 * @return the direction of the robot
	 */
	public Direction getDirection() { return direction; }

	/**
	 * Move the robot one step forward
	 *
	 * @return true or false indicating whether the move action is successful
	 */
	public boolean move() {
		// Try to move the robot
		int tempX = getX() + getDirection().getX();
		int tempY = getY() + getDirection().getY();

		// Check whether the move action will cause the robot to be out of boundary
		if(tempX < 1 || tempY < 1 || tempX > BOARD_SIZE || tempY > BOARD_SIZE) {
			return false;
		}

		// Set the x and y coordinates of the robot is the move action is valid
		setX(tempX);
		setY(tempY);
		return true;
	}

	/**
	 * Turn the robot to face left
	 */
	public void turnLeft() {
		switch (getDirection()) {
			case NORTH: 
				setDirection(Direction.WEST);
				break;
			case EAST:
				setDirection(Direction.NORTH);
				break;
			case SOUTH:
				setDirection(Direction.EAST);
				break;
			case WEST:
				setDirection(Direction.SOUTH);
				break;
		}
	}
	
	/**
	 * Turn the robot to face right
	 */
	public void turnRight() {
		switch (getDirection()) {
			case WEST:
				setDirection(Direction.NORTH);
				break;
			case NORTH:
				setDirection(Direction.EAST);
				break;
			case EAST:
				setDirection(Direction.SOUTH);
				break;
			case SOUTH:
				setDirection(Direction.WEST);
				break;
		}
	}

	/**
	 * Construct the string representation of the robot
	 *
	 * @return String indicating the current status of the robot
	 */
	public String toString() {
		String facing = "";
		
		// Constructing the facing
		switch(getDirection()) {
			case NORTH:
				facing = "North";
				break;
			case EAST:
				facing = "East";
				break;
			case SOUTH:
				facing = "South";
				break;
			case WEST:
				facing = "West";
				break;
		}

		// Construct the location, combine with facing
		return "[" + getX() + ", " + getY() + "]" + " Facing " + facing;
	}
}

