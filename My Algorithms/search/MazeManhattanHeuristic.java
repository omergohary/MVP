/**
 * @file maze3d_ManhattanHeuristic.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an example of heuristic - it calls Manhattan distance
 * 				
 * @date    30/08/2015
 */

package search;

import algorithms.mazeGenerators.Position;

/**
 * The class of the Manhattan distance heuristic
 */
public class MazeManhattanHeuristic implements Heuristic<Position> 
{


	@Override
	public double getHeuristicCost(State<Position> curr, State<Position> end)
	{
		double cost = Math.abs((curr.getState().getLength() - end.getState().getLength()))
					+ Math.abs((curr.getState().getHeight() - end.getState().getHeight()))
					+ Math.abs((curr.getState().getWidth()  - end.getState().getWidth()));
		return cost;
	}
	
	private static final long serialVersionUID = 1867230572541137022L;

}
