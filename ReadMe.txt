challange: Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns. The robot can only move in two directions, right and down, but certain cells are "off limits" such that the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to the bottom right.


Solution explenation:
	Custom Data Type for each point of the grid that points to a previously accessable tile.
	Robot checks both nearby tiles for availability, if they are open, it creates a tile for it pointing to its current tile
	Robot moves to tile prioritizing the closest to the end point, then by which value is bigger(x or y) to maximise information gained.
	once end point is found(or the problem is found to be impossible) the path is generated in a stack from the endpoint to the robot then printed out.
