/**
 * @file maze3d_AirHeuristic.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an example of heuristic - it calls Air distance
 * 				
 * @date    30/08/2015
 */

package search;

import algorithms.mazeGenerators.Position;

/**
 * The class of the air distance heuristic
 */
public class MazeAirHeuristic implements Heuristic<Position> 
{
	/**
	 * An override of the getHeuristicCost function according to the air distance calculate logic
	 */
	@Override
	public double getHeuristicCost(State<Position> curr, State<Position> end) 
	{
		int heightDiff = curr.getState().getHeight() - end.getState().getHeight();
		int widthDiff  = curr.getState().getWidth() - end.getState().getWidth();
		int lengthDiff = curr.getState().getLength() - end.getState().getLength();
		double cost    = Math.sqrt(Math.pow(heightDiff,2) + Math.pow(widthDiff,2) + Math.pow(lengthDiff,2));
		return cost;
	}
	
	private static final long serialVersionUID = 4271519720194952129L;

}
