package com.techreturners.marsRover.util;

import com.techreturners.marsRover.RoverCoordinates;

public interface Util {
    public static final String DELIMITER = " ";
    public static final String REACHED_MAX_LIMIT = "Already reached max limit, provide valid movement input.";
    public static final String REACH_MIN_LIMIT = "Already reached min limit, provide valid movement input.";

    static String finalPosition(RoverCoordinates coordinates) {
        return coordinates.getX() + Util.DELIMITER +
                coordinates.getY() + Util.DELIMITER +
                coordinates.getFacing().value();
    }
}
