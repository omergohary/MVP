/**
 * @file Maze3dSearchable.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an object adapter that makes the maze3d a searchable
 * 				
 * @date    27/08/2015
 */
package demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import search.Searchable;
import search.State;

/**
 * This class is present an adapter to maze3d as Searchable interface
 */
public class Maze3dSearchable implements Searchable<Position>
{
	/** 
	 * C-Tor
	 * 
	 * @param maze - a maze3d object
	 */
	public Maze3dSearchable(Maze3d maze) 
	{
		_maze = maze;
	}
	
	/**
	 *  This function override the getStartState of Searchable interface
	 *  
	 *  @return the start state<position> of the maze3d
	 */
	@Override
	public State<Position> getStartState() 
	{
		return new State<Position>(_maze.getStartPosition());
	}

	/**
	 *  This function override the getGoalState of Searchable interface
	 *  
	 *  @return the goal state<position> of the maze3d
	 */
	@Override
	public State<Position> getGoalState() 
	{
		return new State<Position>(_maze.getGoalPosition());
	}

	/**
	 *  This function override the getAllPossibleStates of Searchable interface
	 *  
	 *  @param s - the required state<position> to check about.
	 *  
	 *  @return an array of state<position> according to the incoming generated maze3d 
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) 
	{
		ArrayList<Position> list = _maze.getPossibleMoves(s.getState());
		ArrayList<State<Position>> stateList = new ArrayList<>();
		
		for (Position pos : list) {
			if(_maze.isPass(pos)) {
				stateList.add(new State<Position>(pos));
			}
		}
		
		return stateList;
	}
	
	/******* Members ******/
	
	private static final long serialVersionUID = 3115395649153098467L;
	private Maze3d _maze;

}
