package com.techreturners.marsRover.movement;

import com.techreturners.marsRover.Direction;
import com.techreturners.marsRover.RoverCoordinates;

import java.util.List;

public interface VehicleMovement {
    void turnRight(RoverCoordinates coordinates);
    void turnLeft(RoverCoordinates coordinates);
    void move(List<String> roverFinalPositions, RoverCoordinates gridMinCoordinates,
              RoverCoordinates gridMaxCoordinates, RoverCoordinates roverFinalCoordinates) throws Exception;
}
