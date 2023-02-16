package com.techreturners.marsRover;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String EXIT = "exit";

    public static void main(String[] args){
        System.out.println("Enter the maximum X and Y coordinates separated by space, eg. 5 5 :");
        Scanner scanner = new Scanner(System.in);
        String max_grid_size = scanner.nextLine();
        MarsRover rover = new MarsRover(max_grid_size);
        System.out.println("Enter number of rovers in the Plateau: ");
        int numberOfRovers = Integer.parseInt(scanner.nextLine().trim());
        while(numberOfRovers>0) {
            System.out.println("Enter the initial position of rover in X and Y coordinates along with rover facing (N, E, S, W) separated by space, eg. 1 2 N:");
            String roverInitialPosition = scanner.nextLine();
            System.out.println("Enter the rover movement commands (R- Right, L- Left, M- Move) without delimiter, eg. LMLMLMLMM:");
            String movements = scanner.nextLine();
            try{
                System.out.println(String.format("Rover final position: %s", rover.navigate(roverInitialPosition, movements)));
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            numberOfRovers--;
        }
    }

}
