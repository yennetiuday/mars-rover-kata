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
	private RoverCoordinates roverInitialCoordinates;
	private RoverCoordinates roverFinalCoordinates;

	private static List<String> roverFinalPositions;

	public MarsRover(String maxSizeOfGrid) {
		gridMinCoordinates = new RoverCoordinates(0, 0, null);
		roverFinalPositions = new ArrayList<>();
		String[] maxSizeOfGridInXYCoordinates = maxSizeOfGrid.trim().split(DELIMITTER);
		if (maxSizeOfGridInXYCoordinates.length != GRID_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid input. Grid size should contain x and y coordinates seperated by space.");
		} else if (isCoordinatesIsNumeric(maxSizeOfGridInXYCoordinates[0], maxSizeOfGridInXYCoordinates[1])) {
			gridMaxCoordinates = new RoverCoordinates(Integer.valueOf(maxSizeOfGridInXYCoordinates[0]),
					Integer.valueOf(maxSizeOfGridInXYCoordinates[1]), null);
		} else {
			throw new NumberFormatException(
					"Invalid input. Grid size should contain integer values of x and y coordinates separated by space.");
		}
		if (isGridSizeLessThanOrigin()) {
			throw new IllegalArgumentException(
					String.format("Plateau should contain positive coordinates with origin at eg. %d %d",
							gridMinCoordinates.getX(), gridMinCoordinates.getY()));
		}
	}

	public String navigate(String initialPosition, String movements) throws Exception {
		convertRoverInitialPosition(initialPosition.trim());
		List<Movement> movementInputs = convertMovementInputToList(movements.trim());
		for (Movement movement : movementInputs) {
			switch (movement) {
			case RIGHT:
				rotateRight();
				break;
			case LEFT:
				rotateLeft();
				break;
			case MOVE:
				try {
					moveRover();
				} catch (Exception e) {
					roverFinalPositions.add(finalPosition());
					throw new Exception(e.getMessage());
				}
				break;
			default:
				break;
			}
		}
		String finalPosition = finalPosition();
		roverFinalPositions.add(finalPosition);
		return finalPosition;
	}

	private String finalPosition() {
		StringBuilder finalPositionBuilder = new StringBuilder().append(roverFinalCoordinates.getX()).append(DELIMITTER)
				.append(roverFinalCoordinates.getY()).append(DELIMITTER)
				.append(roverFinalCoordinates.getFacing().value());
		return finalPositionBuilder.toString();
	}

	private void convertRoverInitialPosition(String roverInitialPosition) {
		String[] initialPosition = roverInitialPosition.split(DELIMITTER);

		if (initialPosition.length != POSITION_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid value provided for Rover initial position. Value input eg. 1 2 N");
		} else if (isCoordinatesIsNumeric(initialPosition[0], initialPosition[1])) {
			roverInitialCoordinates = new RoverCoordinates(Integer.valueOf(initialPosition[0]),
					Integer.valueOf(initialPosition[1]), Direction.valueOfLabel(initialPosition[2]));
			roverFinalCoordinates = new RoverCoordinates(Integer.valueOf(initialPosition[0]),
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
		} else if (Objects.isNull(roverInitialCoordinates.getFacing())) {
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
		return roverInitialCoordinates.getX() > gridMaxCoordinates.getX()
				|| roverInitialCoordinates.getY() > gridMaxCoordinates.getY()
				|| gridMinCoordinates.getX() > roverInitialCoordinates.getX()
				|| gridMinCoordinates.getY() > roverInitialCoordinates.getY();
	}

	private String getAllAvailableDirections() {
		List<String> directions = new ArrayList<>();
		for (Direction e : Direction.values()) {
			directions.add(e.value());
		}
		return directions.stream().collect(Collectors.joining(", "));
	}

	private void rotateRight() {
		switch (roverFinalCoordinates.getFacing()) {
		case NORTH:
			roverFinalCoordinates.setFacing(Direction.EAST);
			break;
		case EAST:
			roverFinalCoordinates.setFacing(Direction.SOUTH);
			break;
		case SOUTH:
			roverFinalCoordinates.setFacing(Direction.WEST);
			break;
		case WEST:
			roverFinalCoordinates.setFacing(Direction.NORTH);
			break;
		default:
			break;
		}
	}

	private void rotateLeft() {
		switch (roverFinalCoordinates.getFacing()) {
		case NORTH:
			roverFinalCoordinates.setFacing(Direction.WEST);
			break;
		case WEST:
			roverFinalCoordinates.setFacing(Direction.SOUTH);
			break;
		case SOUTH:
			roverFinalCoordinates.setFacing(Direction.EAST);
			break;
		case EAST:
			roverFinalCoordinates.setFacing(Direction.NORTH);
			break;
		default:
			break;
		}
	}

	private void moveRover() throws Exception {
		switch (roverFinalCoordinates.getFacing()) {
		case NORTH:
			roverFinalCoordinates.setY(roverFinalCoordinates.getY() + 1);
			if (roverFinalCoordinates.getY() > gridMaxCoordinates.getY()) {
				throw new Exception("Already reached max limit, provide valid movement input.");
			} else if(isCollisionOccurred()){
				roverFinalCoordinates.setY(roverFinalCoordinates.getY() - 1);
				throw new Exception(String.format("Collision occurred rover stopped at: %s", finalPosition()));
			}
			break;
		case EAST:
			roverFinalCoordinates.setX(roverFinalCoordinates.getX() + 1);
			if (roverFinalCoordinates.getX() > gridMaxCoordinates.getX()) {
				throw new Exception("Already reached max limit, provide valid movement input.");
			} else if(isCollisionOccurred()){
				roverFinalCoordinates.setX(roverFinalCoordinates.getX() - 1);
				throw new Exception(String.format("Collision occurred rover stopped at: %s", finalPosition()));
			}
			break;
		case SOUTH:
			roverFinalCoordinates.setY(roverFinalCoordinates.getY() - 1);
			if (roverFinalCoordinates.getY() < gridMinCoordinates.getY()) {
				throw new Exception("Already reached min limit, provide valid movement input.");
			} else if(isCollisionOccurred()){
				roverFinalCoordinates.setY(roverFinalCoordinates.getY() + 1);
				throw new Exception(String.format("Collision occurred rover stopped at: %s", finalPosition()));
			}
			break;
		case WEST:
			roverFinalCoordinates.setX(roverFinalCoordinates.getX() - 1);
			if (roverFinalCoordinates.getX() < gridMinCoordinates.getX()) {
				throw new Exception("Already reached min limit, provide valid movement input.");
			} else if(isCollisionOccurred()){
				roverFinalCoordinates.setX(roverFinalCoordinates.getX() + 1);
				throw new Exception(String.format("Collision occurred rover stopped at: %s", finalPosition()));
			}
			break;
		default:
			break;
		}
	}

	private boolean isCollisionOccurred() {
		return roverFinalPositions.contains(finalPosition());
	}

	public RoverCoordinates getGridMinCoordinates() {
		return gridMinCoordinates;
	}

	public RoverCoordinates getGridMaxCoordinates() {
		return gridMaxCoordinates;
	}

	public RoverCoordinates getRoverInitialCoordinates() {
		return roverInitialCoordinates;
	}

	public RoverCoordinates getRoverFinalCoordinates() {
		return roverFinalCoordinates;
	}

}
