package com.techreturners.marsRover;

import com.techreturners.marsRover.movement.RoverMovementImpl;
import com.techreturners.marsRover.movement.VehicleMovement;
import com.techreturners.marsRover.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarsRover {

	private static final int GRID_SIZE_MAX_LENGTH = 2, POSITION_SIZE_MAX_LENGTH = 3;
	private static List<String> roverFinalPositions;
	private final RoverCoordinates gridMinCoordinates;
	private final RoverCoordinates gridMaxCoordinates;
	private final VehicleMovement roverMovement;
	private RoverCoordinates roverInitialCoordinates;
	private RoverCoordinates roverFinalCoordinates;
	private Util util;

	public MarsRover(String maxSizeOfGrid) {
		gridMinCoordinates = new RoverCoordinates(0, 0, null);
		roverFinalPositions = new ArrayList<>();
		roverMovement = new RoverMovementImpl();
		util = new Util();
		String[] maxSizeOfGridInXYCoordinates = maxSizeOfGrid.trim().split(Util.DELIMITER);
		if (maxSizeOfGridInXYCoordinates.length != GRID_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid input. Grid size should contain x and y coordinates seperated by space.");
		} else if (isCoordinatesIsNumeric(maxSizeOfGridInXYCoordinates[0], maxSizeOfGridInXYCoordinates[1])) {
			gridMaxCoordinates = new RoverCoordinates(Integer.parseInt(maxSizeOfGridInXYCoordinates[0]),
					Integer.parseInt(maxSizeOfGridInXYCoordinates[1]), null);
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
				roverMovement.turnRight(roverFinalCoordinates);
				break;
			case LEFT:
				roverMovement.turnLeft(roverFinalCoordinates);
				break;
			case MOVE:
				try {
					roverMovement.move(roverFinalPositions, gridMinCoordinates,
							gridMaxCoordinates, roverFinalCoordinates);
				} catch (Exception e) {
					roverFinalPositions.add(util.finalPosition(roverFinalCoordinates));
					throw new Exception(e.getMessage());
				}
				break;
			default:
				break;
			}
		}
		String finalPosition = util.finalPosition(roverFinalCoordinates);
		roverFinalPositions.add(finalPosition);
		return finalPosition;
	}

	private void convertRoverInitialPosition(String roverInitialPosition) {
		String[] initialPosition = roverInitialPosition.split(Util.DELIMITER);

		if (initialPosition.length != POSITION_SIZE_MAX_LENGTH) {
			throw new IllegalArgumentException(
					"Invalid value provided for Rover initial position. Value input eg. 1 2 N");
		} else if (isCoordinatesIsNumeric(initialPosition[0], initialPosition[1])) {
			roverInitialCoordinates = new RoverCoordinates(Integer.parseInt(initialPosition[0]),
					Integer.parseInt(initialPosition[1]), Direction.valueOfLabel(initialPosition[2]));
			roverFinalCoordinates = new RoverCoordinates(Integer.parseInt(initialPosition[0]),
					Integer.parseInt(initialPosition[1]), Direction.valueOfLabel(initialPosition[2]));
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
		return String.join(", ", directions);
	}

}
