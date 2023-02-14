package com.techreturners.marsRover;

public class RoverCoordinates {

	private int x;
	private int y;
	private Direction facing;
	
	public RoverCoordinates(int x, int y, Direction facing) {
		this.x = x;
		this.y = y;
		this.facing = facing;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	
}
