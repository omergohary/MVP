/**
 * @file Solve3dMazeCallable.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a callable that return a 3d maze solution (for thread pool)
 * 				
 * @date    25/10/2015
 */

package model;

import java.util.concurrent.Callable;

import algorithms.demo.Maze3dAdapter;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStarSearcher;
import algorithms.search.BFS;
import algorithms.search.Solution;
import algorithms.search.maze3d_AirHeuristic;
import algorithms.search.maze3d_ManhattanHeuristic;

public class Solve3dMazeCallable implements Callable<Solution<Position>> 
{
	Solve3dMazeCallable(String algorithm, Maze3dAdapter adapter)
	{
		m_adapter = adapter;
		m_algorithm = algorithm;
	}
	
	@Override
	public Solution<Position> call() throws Exception 
	{
		switch(m_algorithm)
		{
			case("BFS"):
			case("bfs"):
			{
				// save the solution
				return (new BFS<Position>().search(m_adapter));
			}
			
			case("AirDistance"):
			{
				return (new AStarSearcher<Position>(new maze3d_AirHeuristic()).search(m_adapter));
			}
			
			case("Manhattan"):
			{
				return (new AStarSearcher<Position>(new maze3d_ManhattanHeuristic()).search(m_adapter));
			}
			
			default:
			{
				System.out.println("Bad type of algorithm!");
				return null;
			}
		}
	}
	
	/************ MEMBERS *************/
	
		Maze3dAdapter m_adapter;
	String m_algorithm;

}
