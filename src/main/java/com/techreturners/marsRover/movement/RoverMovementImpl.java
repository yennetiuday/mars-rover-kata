package com.techreturners.marsRover.movement;

import com.techreturners.marsRover.Direction;
import com.techreturners.marsRover.RoverCoordinates;
import com.techreturners.marsRover.util.Util;

import java.util.List;

public class RoverMovementImpl implements VehicleMovement{

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
                    throw new Exception(Util.REACHED_MAX_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setY(roverFinalCoordinates.getY() - 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            case EAST -> {
                roverFinalCoordinates.setX(roverFinalCoordinates.getX() + 1);
                if (roverFinalCoordinates.getX() > gridMaxCoordinates.getX()) {
                    throw new Exception(Util.REACHED_MAX_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setX(roverFinalCoordinates.getX() - 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            case SOUTH -> {
                roverFinalCoordinates.setY(roverFinalCoordinates.getY() - 1);
                if (roverFinalCoordinates.getY() < gridMinCoordinates.getY()) {
                    throw new Exception(Util.REACH_MIN_LIMIT);
                } else if (isCollisionOccurred(roverFinalPositions, roverFinalCoordinates)) {
                    roverFinalCoordinates.setY(roverFinalCoordinates.getY() + 1);
                    throw new Exception(getRoverCollidedMessage(roverFinalCoordinates));
                }
            }
            case WEST -> {
                roverFinalCoordinates.setX(roverFinalCoordinates.getX() - 1);
                if (roverFinalCoordinates.getX() < gridMinCoordinates.getX()) {
                    throw new Exception(Util.REACH_MIN_LIMIT);
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
                Util.finalPosition(roverFinalCoordinates));
    }

    private boolean isCollisionOccurred(List<String> roverFinalPositions, RoverCoordinates roverFinalCoordinates) {
        return roverFinalPositions.contains(Util.finalPosition(roverFinalCoordinates));
    }

}
