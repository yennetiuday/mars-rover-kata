package com.techreturners.marsRover;

public class Rover {

    private static final String DELIMITTER = " ";

    private int max_x, max_y;

    public int getMax_x() {
		return max_x;
	}

	public int getMax_y() {
		return max_y;
	}

	public Rover(String maxSizeOfGrid) {
        String[] maxSizeOfGridInXYCoordinates = maxSizeOfGrid.split(DELIMITTER);
        if(maxSizeOfGridInXYCoordinates.length != 2) {
            throw new IllegalArgumentException("Invalid max grid size");
        }
        max_x = Integer.valueOf(maxSizeOfGridInXYCoordinates[0]);
        max_y = Integer.valueOf(maxSizeOfGridInXYCoordinates[1]);
    }
}
