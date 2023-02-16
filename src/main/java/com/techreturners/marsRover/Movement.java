package com.techreturners.marsRover;

public enum Movement {

	RIGHT("R"), LEFT("L"), MOVE("M");

	private final String value;

	private Movement(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}

	public static Movement valueOfLabel(String value) {
		for (Movement m : values()) {
			if (m.value.equals(value.toUpperCase())) {
				return m;
			}
		}
		return null;
	}
}
