class Robot {
	private int x;
	private int y;
	private Direction direction;

	public Robot(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	private void setX(int x) { this.x = x; }
	
	private void setY(int y) { this.y = y; }

	private void setDirection(Direction direction) {this.direction = direction; }

	public int getX() { return x; }

	public int getY() { return y; }

	public Direction getDirection() { return direction; }

	public boolean move() {
		int tempX = getX() + getDirection().getX();
		int tempY = getY() + getDirection().getY();
		if(tempX < 1 || tempY < 1 || tempX > 8 || tempY > 8) {
			return false;
		}
		setX(tempX);
		setY(tempY);
		return true;
	}

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

	public String toString() {
		String facing = "";

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

		return "[" + getX() + ", " + getY() + "]" + " Facing " + facing;
	}

}

