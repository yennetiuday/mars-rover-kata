package com.techreturners.marsRover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoverTest {

    private Rover rover;

    @BeforeEach
    void setUp() {
        rover = new Rover("5 4");
    }

    @Test
    public void testConvertInputMaxGridSize() {
        assertEquals(5, rover.getMax_x());
        assertEquals(4, rover.getMax_y());
    }
}