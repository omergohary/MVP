/**
 * @file Command_saveMaze.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to solve a maze
 * 				
 * @date    24/09/2015
 */

package presenter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * The command that responsible on "solve" request
 */
public class Command_solve implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 */
	public Command_solve(Model model)
	{
		m_model = model;
	}

	/**
	 * This function calls the model to solve the maze
	 * @param args[0] = mazeName  
	 * 	  	  argv[1] = algorithm  - BFS / AirDistance / Manhattan
	 * 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Override
	public void doCommand(String args) throws IOException, InterruptedException, ExecutionException 
	{		
		String[] arr = args.split(" ", 2);
		
		m_model.solveMaze(arr[0], arr[1]);
	}
	
	/************ Member **********/
	Model m_model;
}
