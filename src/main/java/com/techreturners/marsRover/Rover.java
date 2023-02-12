package com.techreturners.marsRover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Rover {

	private static final String DELIMITTER = " ";
	private static final int MIN_X = 0, MIN_Y = 0, GRID_SIZE_MAX_LENGTH = 2, POSITION_SIZE_MAX_LENGTH = 3;

	private int max_x, max_y, initial_x, initial_y, final_x, final_y;
	private Direction initial_facing, final_facing;

	public Rover(String maxSizeOfGrid) {
		String[] maxSizeOfGridInXYCoordinates = maxSizeOfGrid.split(DELIMITTER);
		if (maxSizeOfGridInXYCoordinates.length != GRID_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid input. Grid size should contain x and y coordinates seperated by space.");
		} else if (isCoordinatesIsNumeric(maxSizeOfGridInXYCoordinates[0], maxSizeOfGridInXYCoordinates[1])) {
			max_x = Integer.valueOf(maxSizeOfGridInXYCoordinates[0]);
			max_y = Integer.valueOf(maxSizeOfGridInXYCoordinates[1]);
		} else {
			throw new NumberFormatException(
					"Invalid input. Grid size should contain integer values of x and y coordinates seperated by space.");
		}

		if (isGridSizeLessThanOrigin()) {
			throw new IllegalArgumentException(String
					.format("Plateau should contain positive coordinates with origin at eg. %d %d", MIN_X, MIN_Y));
		}
	}

	public void convertRoverInitialPosition(String roverInitialPosition) {
		String[] initialPosition = roverInitialPosition.split(DELIMITTER);

		if (initialPosition.length != POSITION_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid value provided for Rover initial position. Value input eg. 1 2 N");
		} else if (isCoordinatesIsNumeric(initialPosition[0], initialPosition[1])) {
			initial_x = final_x = Integer.valueOf(initialPosition[0]);
			initial_y = final_y = Integer.valueOf(initialPosition[1]);
			initial_facing = final_facing = Direction.valueOfLabel(initialPosition[2]);
		} else {
			throw new NumberFormatException(
					"Invalid input. Rovar Position should contain integer values of x, y coordinates and facing direction seperated by space.");
		}

		if (isInitialPositionInLimitsOfPlateau()) {
			throw new IllegalArgumentException(
					String.format("Invalid Rover initial position. Value should be between %d %d and %d %d", MIN_X,
							MIN_Y, max_x, max_y));
		} else if (Objects.isNull(initial_facing)) {
			throw new IllegalArgumentException(
					String.format("Invalid Rover initial position. Direction should be one of the following %s",
							getAllAvailableDirections()));
		}
	}

	public List<Movement> convertMovementInputToList(String movementInput) {
		List<Movement> inputs = new ArrayList<>();
		for (String input : movementInput.split("")) {
			Movement movement = Movement.valueOfLabel(input);
			if (Objects.nonNull(movement)) {
				inputs.add(movement);
			} else {
				throw new IllegalArgumentException("Invalid movement input. Values should be from R L and M.");
			}
		}
		return inputs;
	}

	public String navigate(String initialPosition, String movements) throws Exception {
		convertRoverInitialPosition(initialPosition);
		List<Movement> movementInputs = convertMovementInputToList(movements);
		for (Movement movement : movementInputs) {
			switch (movement) {
			case RIGHT:
				rotateRight();
				break;
			case LEFT:
				rotateLeft();
				break;
			case MOVE:
				moveRover();
				break;
			default:
				break;
			}
		}
		return finalPosition();
	}

	private String finalPosition() {
		StringBuilder finalPositionBuilder = new StringBuilder()
				.append(final_x).append(DELIMITTER).append(final_y)
				.append(DELIMITTER).append(final_facing.value());
		return finalPositionBuilder.toString();
	}

	private void rotateRight() {
		switch (final_facing) {
		case NORTH:
			final_facing = Direction.EAST;
			break;
		case EAST:
			final_facing = Direction.SOUTH;
			break;
		case SOUTH:
			final_facing = Direction.WEST;
			break;
		case WEST:
			final_facing = Direction.NORTH;
			break;
		default:
			break;
		}
	}

	private void rotateLeft() {
		switch (final_facing) {
		case NORTH:
			final_facing = Direction.WEST;
			break;
		case WEST:
			final_facing = Direction.SOUTH;
			break;
		case SOUTH:
			final_facing = Direction.EAST;
			break;
		case EAST:
			final_facing = Direction.NORTH;
			break;
		default:
			break;
		}		
	}
	
	private void moveRover() throws Exception {
		switch (final_facing) {
		case NORTH:
			final_y++;
			if(final_y > max_y)
				throw new Exception("Already reached max limit, provide valid movement input.");
			break;
		case EAST:
			final_x++;
			if(final_x > max_x)
				throw new Exception("Already reached max limit, provide valid movement input.");
			break;
		case SOUTH:
			final_y--;
			if(final_y < MIN_Y)
				throw new Exception("Already reached min limit, provide valid movement input.");
			break;
		case WEST:
			final_x--;
			if(final_x < MIN_X)
				throw new Exception("Already reached min limit, provide valid movement input.");
			break;
		default:
			break;
		}
	}

	private boolean isCoordinatesIsNumeric(String x, String y) {
		return isNumeric(x) && isNumeric(y);
	}

	private String getAllAvailableDirections() {
		List<String> directions = new ArrayList<>();
		for (Direction e : Direction.values()) {
			directions.add(e.value());
		}
		return directions.stream().collect(Collectors.joining(", "));
	}

	private boolean isInitialPositionInLimitsOfPlateau() {
		return initial_x > max_x || initial_y > max_y || MIN_X > initial_x || MIN_Y > initial_y;
	}

	private boolean isGridSizeLessThanOrigin() {
		return max_x < MIN_X || max_y < MIN_Y;
	}

	private static boolean isNumeric(String number) {
		if (number == null) {
			return false;
		}
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
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

	public Direction getInitial_facing() {
		return initial_facing;
	}

}
