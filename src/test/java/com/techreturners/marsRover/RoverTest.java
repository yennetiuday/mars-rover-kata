package com.techreturners.marsRover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

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

	@Test
	public void testWrongValueInputForMaxSize() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rover("-1 2 2"));
		assertEquals("Invalid input. Grid size should contain x and y coordinates seperated by space.",
				exception.getMessage());
	}

	@Test
	public void testWrongValueInputForMaxSizeWithNegativeValues() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rover("-1 2"));
		assertEquals("Plateau should contain positive coordinates with origin at eg. 0 0", exception.getMessage());
	}

	@Test
	public void testWrongValueInputForMaxSizeWithNonNumericValues() {
		NumberFormatException exception = assertThrows(NumberFormatException.class, () -> new Rover("N 1 "));
		assertEquals(
				"Invalid input. Grid size should contain integer values of x and y coordinates seperated by space.",
				exception.getMessage());
	}

	@Test
	public void testConvertRoverInitialPosition() {
		rover.convertRoverInitialPosition("1 2 N");
		assertEquals(1, rover.getInitial_x());
		assertEquals(2, rover.getInitial_y());
		assertEquals("N", rover.getInitial_direction().label);
	}

	@Test
	public void testWrongValueInputForRoverInitialPosition() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rover.convertRoverInitialPosition("1 2"));
		assertEquals("Invalid value provided for Rover initial position. Value input eg. 1 2 N",
				exception.getMessage());
	}

	@Test
	public void testWrongValueInputForRoverInitialPositionWithNegativeValues() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rover.convertRoverInitialPosition("-1 2 N"));
		assertEquals("Invalid Rover initial position. Value should be between 0 0 and 5 4", exception.getMessage());
	}

	@Test
	public void testWrongValueInputForRoverInitialPositionWithValuesGreaterThanGridSize() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rover.convertRoverInitialPosition("1 7 N"));
		assertEquals("Invalid Rover initial position. Value should be between 0 0 and 5 4", exception.getMessage());
	}

	@Test
	public void testWrongValueInputForRoverInitialPositionWithInvalidDirection() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> rover.convertRoverInitialPosition("1 2 Q"));
		assertEquals("Invalid Rover initial position. Direction should be one of the following N, S, E, W",
				exception.getMessage());
	}

	@Test
	public void testWrongValueInputForRoverInitialPositionWithNonNumericValues() {
		NumberFormatException exception = assertThrows(NumberFormatException.class,
				() -> rover.convertRoverInitialPosition("N 2 2"));
		assertEquals(
				"Invalid input. Rovar Position should contain integer values of x, y coordinates and facing direction seperated by space.",
				exception.getMessage());
	}

	@Test
	public void testConvertMovementInput() {
		assertEquals(new ArrayList<>(Arrays.asList("M")) , rover.convertMovementInputToList("M"));
	}
	
	@Test
	public void testConvertMovementInputWithMoreValues() {
		assertEquals(new ArrayList<>(Arrays.asList("R", "M", "L", "L")) , rover.convertMovementInputToList("RMLL"));
	}

}