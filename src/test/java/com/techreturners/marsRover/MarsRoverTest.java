package com.techreturners.marsRover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarsRoverTest {

	private MarsRover rover;

	@BeforeEach
	void setUp() {
		rover = new MarsRover("5 5");
	}

	@Test
	public void testConvertInputMaxGridSize() {
		assertEquals(5, rover.getGridMaxCoordinates().getX());
		assertEquals(5, rover.getGridMaxCoordinates().getY());
	}

	@Test
	public void testWrongValueInputForMaxSize() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new MarsRover("-1 2 2"));
		assertEquals("Invalid input. Grid size should contain x and y coordinates seperated by space.",
				exception.getMessage());
	}

	@Test
	public void testWrongValueInputForMaxSizeWithNegativeValues() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new MarsRover("-1 2"));
		assertEquals("Plateau should contain positive coordinates with origin at eg. 0 0", exception.getMessage());
	}

	@Test
	public void testWrongValueInputForMaxSizeWithNonNumericValues() {
		NumberFormatException exception = assertThrows(NumberFormatException.class, () -> new MarsRover("N 1 "));
		assertEquals(
				"Invalid input. Grid size should contain integer values of x and y coordinates seperated by space.",
				exception.getMessage());
	}
	
	@Test
	public void testMoveRight() throws Exception {
		assertEquals("1 2 E", rover.navigate("1 2 N", "R"));
	}
	
	@Test
	public void testRotateRightTwice() throws Exception {
		assertEquals("1 2 S", rover.navigate("1 2 N", "RR"));
	}
	
	@Test
	public void testRotateLeftTwice() throws Exception {
		assertEquals("1 2 S", rover.navigate("1 2 N", "LL"));
	}
	
	@Test
	public void testMoveRover() throws Exception {
		assertEquals("1 3 N", rover.navigate("1 2 N", "LMLMLMLMM"));
	}
	
	@Test
	public void testMoveRover2() throws Exception {
		assertEquals("5 1 E", rover.navigate("3 3 E", "MMRMMRMRRM"));
	}
	
	@Test
	public void testMoveRoverMovementInputOverMaxLimit() {
		Exception exception = assertThrows(Exception.class, () -> rover.navigate("3 3 E", "MMRMMRMRRMM"));
		assertEquals("Already reached max limit, provide valid movement input." , exception.getMessage());
	}
	
	@Test
	public void testMoveRoverMovementInputLessThanMinLimit() {
		Exception exception = assertThrows(Exception.class, () -> rover.navigate("3 3 E", "MMRMMMMRMRRMM"));
		assertEquals("Already reached min limit, provide valid movement input." , exception.getMessage());
	}
	
}