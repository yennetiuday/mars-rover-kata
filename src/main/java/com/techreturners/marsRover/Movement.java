package com.techreturners.marsRover;

public enum Movement {

	RIGHT("R"), LEFT("L"), MOVE("M");

	public final String label;

	private Movement(String label) {
		this.label = label;
	}

	public static Movement valueOfLabel(String label) {
		for (Movement m : values()) {
			if (m.label.equals(label)) {
				return m;
			}
		}
		return null;
	}
}
