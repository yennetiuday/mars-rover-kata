package com.techreturners.marsRover;

public enum Direction {

	NORTH("N"), 
	SOUTH("S"),
	EAST("E"),
	WEST("W");
	
	public final String label;
	
	private Direction(String label) {
		this.label = label;
	}
	
	public static Direction valueOfLabel(String label) {
	    for (Direction d : values()) {
	        if (d.label.equals(label)) {
	            return d;
	        }
	    }
	    return null;
	}
}
