package com.techreturners.marsRover.util;

import com.techreturners.marsRover.RoverCoordinates;

public class Util {
    public static String DELIMITER = " ";

    public String finalPosition(RoverCoordinates coordinates) {
        return coordinates.getX() + Util.DELIMITER +
                coordinates.getY() + Util.DELIMITER +
                coordinates.getFacing().value();
    }
}
