package com.techreturners.marsRover.movement;

import com.techreturners.marsRover.Direction;
import com.techreturners.marsRover.RoverCoordinates;
import com.techreturners.marsRover.util.Util;

import java.util.List;

public class RoverMovementImpl implements VehicleMovement{

    private Util util;
    private static final String REACHED_MAX_LIMIT = "Already reached max limit, provide valid movement input.";
    private static final String REACH_MIN_LIMIT = "Already reached min limit, provide valid movement input.";

    public RoverMovementImpl() {
        util = new Util();
    }

    @Override
    public void turnRight(RoverCoordinates coordinates) {
        switch (coordinates.getFacing()) {
            case NORTH -> coordinates.setFacing(Direction.EAST);
            case EAST -> coordinates.setFacing(Direction.SOUTH);
            case SOUTH -> coordinates.setFacing(Direction.WEST);
            case WEST -> coordinates.setFacing(Direction.NORTH);
            default -> {
            }
        }
    }

    @Override
    public void turnLeft(RoverCoordinates coordinates) {
        switch (coordinates.getFacing()) {
            case NORTH -> coordinates.setFacing(Direction.WEST);
            case WEST -> coordinates.setFacing(Direction.SOUTH);
            case SOUTH -> coordinates.setFacing(Direction.EAST);
            case EAST -> coordinates.setFacing(Direction.NORTH);
            default -> {
            }
        }
    }

    @Override
    public void move(List<String> roverFinalPositions, RoverCoordinates gridMinCoordinates,
                     RoverCoordinates gridMaxCoordinates, RoverCoordinates roverFinalCoordinates) throws Exception {
        switch (roverFinalCoordinates.getFacing()) {
            case NORTH -> {
                roverFinalCoordinates.setY(roverFinalCoordinates.getY() + 1);
                if (roverFinalCoordinates.getY() > gridMaxCoordinates.getY()) {
                    throw new Exception(REACHED_MAX_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setY(roverFinalCoordinates.getY() - 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            case EAST -> {
                roverFinalCoordinates.setX(roverFinalCoordinates.getX() + 1);
                if (roverFinalCoordinates.getX() > gridMaxCoordinates.getX()) {
                    throw new Exception(REACHED_MAX_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setX(roverFinalCoordinates.getX() - 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            case SOUTH -> {
                roverFinalCoordinates.setY(roverFinalCoordinates.getY() - 1);
                if (roverFinalCoordinates.getY() < gridMinCoordinates.getY()) {
                    throw new Exception(REACH_MIN_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setY(roverFinalCoordinates.getY() + 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            case WEST -> {
                roverFinalCoordinates.setX(roverFinalCoordinates.getX() - 1);
                if (roverFinalCoordinates.getX() < gridMinCoordinates.getX()) {
                    throw new Exception(REACH_MIN_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setX(roverFinalCoordinates.getX() + 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            default -> {
            }
        }
    }

    private String getRoverCollidedMessage(RoverCoordinates roverFinalCoordinates) {
        return String.format("Collision occurred rover stopped at: %s",
                util.finalPosition(roverFinalCoordinates));
    }

    private boolean isCollisionOccurred(List<String> roverFinalPositions, RoverCoordinates roverFinalCoordinates) {
        return roverFinalPositions.contains(util.finalPosition(roverFinalCoordinates));
    }

}
