# Mars Rover Kata
Create a program to move rovers around the surface of Mars.
The surface of Mars is represented by a Plateau. It is assumed that the Plateau is square/rectangular grid for the purpose of this task.

## Notes on accepting user inputs :


1. First line of input to the program represents the upper-right coordinates of the Plateau.
2. Eg. 5 5 This Plateau has maximum (x, y) coordinates of (5, 5).
3. Lower left coordinates of the plateau is (0, 0)
4. Subsequent line of input represents instruction to move the rovers.
5. First line of input to a rover represent the starting point of the rover eg. 1 2 N
5. Here 1 2 are X and Y co-ordinates on the grid.
6. N is the direction it is facing i.e. N, S, E, W
7. Second line the instructions to move the Rover around the Plateau.
8. eg. RLM Here M to move the Rover forward by one grid, L to spin 90 degrees Left without moving from current coordinates, R spins the Rover 90 degrees Right without moving from the current coordinate.
9. Rovers move sequentially, this means the Rover needs to finish moving first before the next one can move.