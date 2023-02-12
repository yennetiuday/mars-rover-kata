package com.techreturners.marsRover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Rover {

	private static final String DELIMITTER = " ";
	private static final int MIN_X = 0, MIN_Y = 0, GRID_SIZE_MAX_LENGTH = 2, POSITION_SIZE_MAX_LENGTH = 3;

	private int max_x, max_y, initial_x, initial_y;
	private Direction initial_direction;

	public Rover(String maxSizeOfGrid) {
		String[] maxSizeOfGridInXYCoordinates = maxSizeOfGrid.split(DELIMITTER);
		if (maxSizeOfGridInXYCoordinates.length != GRID_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid input. Grid size should contain x and y coordinates seperated by space.");
		}
		max_x = Integer.valueOf(maxSizeOfGridInXYCoordinates[0]);
		max_y = Integer.valueOf(maxSizeOfGridInXYCoordinates[1]);

		if (isGridSizeLessThanOrigin()) {
			throw new IllegalArgumentException(String
					.format("Plateau should contain positive coordinates with origin at eg. %d %d", MIN_X, MIN_Y));
		}
	}

	public int getMax_x() {
		return max_x;
	}

	public int getMax_y() {
		return max_y;
	}

	public int getInitial_x() {
		return initial_x;
	}

	public int getInitial_y() {
		return initial_y;
	}

	public Direction getInitial_direction() {
		return initial_direction;
	}

	public void convertRoverInitialPosition(String roverInitialPosition) {
		String[] initialPosition = roverInitialPosition.split(DELIMITTER);

		if (initialPosition.length != POSITION_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid value provided for Rover initial position. Value input eg. 1 2 N");
		}

		initial_x = Integer.valueOf(initialPosition[0]);
		initial_y = Integer.valueOf(initialPosition[1]);
		initial_direction = Direction.valueOfLabel(initialPosition[2]);

		if (isInitialPositionInLimitsOfPlateau()) {
			throw new IllegalArgumentException(
					String.format("Invalid Rover initial position. Value should be between %d %d and %d %d", MIN_X,
							MIN_Y, max_x, max_y));
		} else if (Objects.isNull(initial_direction)) {
			throw new IllegalArgumentException(
					String.format("Invalid Rover initial position. Direction should be one of the following %s",
							getAllAvailableDirections()));
		}
	}

	private String getAllAvailableDirections() {
		List<String> directions = new ArrayList<>();
		for (Direction e : Direction.values()) {
			directions.add(e.label);
		}
		return directions.stream().collect(Collectors.joining(", "));
	}

	private boolean isInitialPositionInLimitsOfPlateau() {
		return initial_x > max_x || initial_y > max_y || MIN_X > initial_x || MIN_Y > initial_y;
	}

	private boolean isGridSizeLessThanOrigin() {
		return max_x < MIN_X || max_y < MIN_Y;
	}

}
