/**
 * @file view.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface to the view in the MVC
 * 				
 * @date    24/09/2015
 */

package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Presenter;

public interface View 
{
	/**
	 * This function is responsible to set the command container to the view part (MVC)
	 * 
	 * @param mapToSet - an instance of hashMap that connect between string and their commands
	 */
	public void SetCommandsContainer(HashMap<String, Command> mapToSet);

	/**
	 * This function is responsible to start the view's job
	 * @throws IOException
	 */
	public void start() throws IOException;
	
	/**
	 * This function is responsible to print to the out stream a string 
	 *
	 * @param stringToPrint - the string to print
	 */
	public void Print(String stringToPrint);
	
	/**
	 * This function is responsible to display a 3d maze to the out stream 
	 * 
	 * @param maze3dToDisplay - the 3d maze to display
	 */
	public void display3dMaze(Maze3d maze3dToDisplay);
	
	/**
	 * This function is responsible to display a 2d maze to the out stream 
	 * 
	 * @param maze3dToDisplay - the 2d maze to display (in int[][] instance)
	 */
	public void display2dMaze(int[][] maze2dToDisplay);
	
	/**
	 * This function is responsible to display a solution to a 3d maze
	 * 
	 * @param maze3dToDisplay - the solution to display
	 * 
	 */	
	public void printSolution(Solution<Position> solutionToPrint);
	
	/**
	 * This function is responsible to shut down the view.
	 * @throws IOException
	 */
	public void shutDown() throws IOException;
}
