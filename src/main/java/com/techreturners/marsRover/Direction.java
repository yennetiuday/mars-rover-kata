package com.techreturners.marsRover;

public enum Direction {

	NORTH("N"), 
	SOUTH("S"),
	EAST("E"),
	WEST("W");
	
	private final String value;
	
	private Direction(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static Direction valueOfLabel(String value) {
	    for (Direction d : values()) {
	        if (d.value.equals(value)) {
	            return d;
	        }
	    }
	    return null;
	}
}
