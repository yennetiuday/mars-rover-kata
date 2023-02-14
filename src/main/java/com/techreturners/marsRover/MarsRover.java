package com.techreturners.marsRover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MarsRover {

	private static final String DELIMITTER = " ";
	private static final int GRID_SIZE_MAX_LENGTH = 2, POSITION_SIZE_MAX_LENGTH = 3;

	private RoverCoordinates gridMinCoordinates;
	private RoverCoordinates gridMaxCoordinates;
	private RoverCoordinates rovarInitialCoordinates;
	private RoverCoordinates rovarFinalCoordinates;

	public MarsRover(String maxSizeOfGrid) {
		gridMinCoordinates = new RoverCoordinates(0, 0, null);

		String[] maxSizeOfGridInXYCoordinates = maxSizeOfGrid.split(DELIMITTER);
		if (maxSizeOfGridInXYCoordinates.length != GRID_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid input. Grid size should contain x and y coordinates seperated by space.");
		} else if (isCoordinatesIsNumeric(maxSizeOfGridInXYCoordinates[0], maxSizeOfGridInXYCoordinates[1])) {
			gridMaxCoordinates = new RoverCoordinates(Integer.valueOf(maxSizeOfGridInXYCoordinates[0]),
					Integer.valueOf(maxSizeOfGridInXYCoordinates[1]), null);
		} else {
			throw new NumberFormatException(
					"Invalid input. Grid size should contain integer values of x and y coordinates seperated by space.");
		}

		if (isGridSizeLessThanOrigin()) {
			throw new IllegalArgumentException(
					String.format("Plateau should contain positive coordinates with origin at eg. %d %d",
							gridMinCoordinates.getX(), gridMinCoordinates.getY()));
		}
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
		StringBuilder finalPositionBuilder = new StringBuilder().append(rovarFinalCoordinates.getX()).append(DELIMITTER)
				.append(rovarFinalCoordinates.getY()).append(DELIMITTER)
				.append(rovarFinalCoordinates.getFacing().value());
		return finalPositionBuilder.toString();
	}

	private void convertRoverInitialPosition(String roverInitialPosition) {
		String[] initialPosition = roverInitialPosition.split(DELIMITTER);

		if (initialPosition.length != POSITION_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid value provided for Rover initial position. Value input eg. 1 2 N");
		} else if (isCoordinatesIsNumeric(initialPosition[0], initialPosition[1])) {
			rovarInitialCoordinates = new RoverCoordinates(Integer.valueOf(initialPosition[0]),
					Integer.valueOf(initialPosition[1]), Direction.valueOfLabel(initialPosition[2]));
			rovarFinalCoordinates = new RoverCoordinates(Integer.valueOf(initialPosition[0]),
					Integer.valueOf(initialPosition[1]), Direction.valueOfLabel(initialPosition[2]));
		} else {
			throw new NumberFormatException(
					"Invalid input. Rovar Position should contain integer values of x, y coordinates and facing direction seperated by space.");
		}

		if (isInitialPositionInLimitsOfPlateau()) {
			throw new IllegalArgumentException(
					String.format("Invalid Rover initial position. Value should be between %d %d and %d %d",
							gridMinCoordinates.getX(), gridMinCoordinates.getY(), gridMaxCoordinates.getX(),
							gridMaxCoordinates.getY()));
		} else if (Objects.isNull(rovarInitialCoordinates.getFacing())) {
			throw new IllegalArgumentException(
					String.format("Invalid Rover initial position. Direction should be one of the following %s",
							getAllAvailableDirections()));
		}
	}

	private List<Movement> convertMovementInputToList(String movementInput) {
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

	private boolean isCoordinatesIsNumeric(String x, String y) {
		return isNumeric(x) && isNumeric(y);
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

	private boolean isGridSizeLessThanOrigin() {
		return gridMaxCoordinates.getX() < gridMinCoordinates.getX()
				|| gridMaxCoordinates.getY() < gridMinCoordinates.getY();
	}

	private boolean isInitialPositionInLimitsOfPlateau() {
		return rovarInitialCoordinates.getX() > gridMaxCoordinates.getX()
				|| rovarInitialCoordinates.getY() > gridMaxCoordinates.getY()
				|| gridMinCoordinates.getX() > rovarInitialCoordinates.getX()
				|| gridMinCoordinates.getY() > rovarInitialCoordinates.getY();
	}

	private String getAllAvailableDirections() {
		List<String> directions = new ArrayList<>();
		for (Direction e : Direction.values()) {
			directions.add(e.value());
		}
		return directions.stream().collect(Collectors.joining(", "));
	}

	private void rotateRight() {
		switch (rovarFinalCoordinates.getFacing()) {
		case NORTH:
			rovarFinalCoordinates.setFacing(Direction.EAST);
			break;
		case EAST:
			rovarFinalCoordinates.setFacing(Direction.SOUTH);
			break;
		case SOUTH:
			rovarFinalCoordinates.setFacing(Direction.WEST);
			break;
		case WEST:
			rovarFinalCoordinates.setFacing(Direction.NORTH);
			break;
		default:
			break;
		}
	}

	private void rotateLeft() {
		switch (rovarFinalCoordinates.getFacing()) {
		case NORTH:
			rovarFinalCoordinates.setFacing(Direction.WEST);
			break;
		case WEST:
			rovarFinalCoordinates.setFacing(Direction.SOUTH);
			break;
		case SOUTH:
			rovarFinalCoordinates.setFacing(Direction.EAST);
			break;
		case EAST:
			rovarFinalCoordinates.setFacing(Direction.NORTH);
			break;
		default:
			break;
		}
	}

	private void moveRover() throws Exception {
		switch (rovarFinalCoordinates.getFacing()) {
		case NORTH:
			rovarFinalCoordinates.setY(rovarFinalCoordinates.getY() + 1);
			if (rovarFinalCoordinates.getY() > gridMaxCoordinates.getY())
				throw new Exception("Already reached max limit, provide valid movement input.");
			break;
		case EAST:
			rovarFinalCoordinates.setX(rovarFinalCoordinates.getX() + 1);
			if (rovarFinalCoordinates.getX() > gridMaxCoordinates.getX())
				throw new Exception("Already reached max limit, provide valid movement input.");
			break;
		case SOUTH:
			rovarFinalCoordinates.setY(rovarFinalCoordinates.getY() - 1);
			if (rovarFinalCoordinates.getY() < gridMinCoordinates.getY())
				throw new Exception("Already reached min limit, provide valid movement input.");
			break;
		case WEST:
			rovarFinalCoordinates.setX(rovarFinalCoordinates.getX() - 1);
			if (rovarFinalCoordinates.getX() < gridMinCoordinates.getX())
				throw new Exception("Already reached min limit, provide valid movement input.");
			break;
		default:
			break;
		}
	}

	public RoverCoordinates getGridMinCoordinates() {
		return gridMinCoordinates;
	}

	public RoverCoordinates getGridMaxCoordinates() {
		return gridMaxCoordinates;
	}

	public RoverCoordinates getRovarInitialCoordinates() {
		return rovarInitialCoordinates;
	}

	public RoverCoordinates getRovarFinalCoordinates() {
		return rovarFinalCoordinates;
	}

}
