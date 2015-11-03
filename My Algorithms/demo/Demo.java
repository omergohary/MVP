/**
 * @file Demo.java
 * 
 * @author Omer Gohary
 * 
 * @description This file is responsible to check the search algorithms (BFS, A*)
 * 				
 * @date    30/08/2015
 */


package demo;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;
import search.Searchable;
import search.Searcher;
import search.Solution;

/**
 * This class represents a demo class that checks 'search' package
 */
public class Demo 
{
	/**
	 * This function is responsible to check a searchable object with a search algorithm
	 * @param name       - the name of the searcher
	 * @param searcher   - the searcher object
	 * @param searchable - the searchable object
	 */
	public void testSearcher(String name, Searcher<Position> searcher, Searchable<Position> searchable)
	{
		 Solution<Position> sol = searcher.search(searchable);
		 
		 System.out.println("The solution of "+ name + ":");
		 sol.printSolution();
		 
		 int n = searcher.getNumberOfNodesEvaluated();
		 
		 System.out.println("The num of steps in "+ name + " is: " + n); // TODO
	}

	/**
	 * The main demo function
	 */

	public void run() 
	{
		// Create the maze and print to the screen
		// NOTE FOR TESTER: "myMaze3dGenerator has some problems, therefore i isolated this problem and work with the simpleGenerator
		Maze3d generatedMaze = new SimpleMaze3dGenerator().generate(6, 6, 6);
		generatedMaze.printMaze();
		
		System.out.println("The Start point is: " + generatedMaze.getStartPosition() +
				           ", and the Goal point is: " + generatedMaze.getGoalPosition());
		
		// Create the maze3d adapter (type of searchable)
		Maze3dSearchable maze3dAdapter = new Maze3dSearchable(generatedMaze);
		
		// Test BFS
		testSearcher("BFS", new BFSSearcher<Position>(), maze3dAdapter);
		
		// Test A* with air distance heuristic
		testSearcher("air distance", new AstarSearcher<Position>(new MazeAirHeuristic()), maze3dAdapter);
		
		// Test A* with Manhattan distance heuristic
		testSearcher("Manhattan" ,new AstarSearcher<Position>(new MazeManhattanHeuristic()), maze3dAdapter);
	}
}
