# A_Algorithm
Using A* Algorithm to compute an optimal path in a discretized map

Designed a discretized map (including four highways and three types of cells: blocked, unblocked, partially blocked) using JavaFX

We will consider grid maps of dimension 160 columns and 120 rows. Initialize these maps by setting all cells in the beginning to correspond to unblocked cells. The cost of transitioning between two regular unblocked cells is 1 if the agent moves horizontally or vertically and 2^(1/2) if the agent moves diagonally.

Then, decide the placement of harder to traverse cells. To do so, select eight coordinates randomly (Xrandom, Yrandom). For each coordinate pair (Xrandom, Yrandom), consider the 31x31 region centered at this coordinate pair. For every cell inside this region, choose with probability 50% to mark it as a hard to traverse cell. The cost of transitioning into such hard to traverse cells is double the cost of moving over regular unblocked cells, i.e.
    moving horizontally or vertically between two hard to traverse cells has a cost of 2;
	moving diagonally between two hard to traverse cells has a cost of 8^(1/2);
	moving horizontally or vertically between a regular unblocked cell and a hard to traverse cell (in either direction) has a cost of 1.5;
	moving diagonally between a regular unblocked cell and a hard to traverse cell (in either direction) has a cost of (2^(1/2) + 8^(1/2)) / 2;
	
The next step is to select four paths on the map that allow the agent to move faster along them (i.e., the “rivers” or highways). Allow only 4-way connectivity for these paths, i.e., these highways are allowed to propagate only horizontally or vertically. For each one of these paths, start with a random cell at the boundary of the grid world. Then, move in a random horizontal or vertical direction for 20 cells but away from the boundary and mark this sequence of cells as containing a highway. To continue, with 60% probability select to move in the same direction and 20% probability select to move in a perpendicular direction (turn the highway left or turn the highway right). Again mark 20 cells as a highway along the selected direction. If you hit a cell that is already a highway in this process, reject the path and start the process again. Continue marking cells in this manner, until you hit the boundary again. If the length of the path is less than 100 cells when you hit the boundary, then reject the path and start the process again. If you cannot add a highway given the placement of the previous rivers, start the process from the beginning. In terms of defining costs, if we are starting from a cell that contains a highway and we are moving horizontally or vertically into a cell that also contains a highway, the cost of this motion is four times less than it would be otherwise (i.e., 0.25 if both cells are regular, 0.5 if both cells are hard to traverse and 0.375 if we are moving between a regular unblocked cell and a hard to traverse cell).

Choose a start point and a goal point. 


Constructed optimal paths using weighted A*, uniform, and other methods between start point and goal
point

Calculated the difference of time expenses, memory costs and relative performances between algorithms in
order to find a best one
