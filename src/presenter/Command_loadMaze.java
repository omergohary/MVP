/**
 * @file Command_loadMaze.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to de-compress maze from file and insert to the saved mazes list
 * 				
 * @date    24/09/2015
 */

package presenter;

import java.io.File;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * The command that responsible on "loadMaze" request
 */
public class Command_loadMaze implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 */
	public Command_loadMaze(Model model)
	{
		m_model = model;
	}

	/**
	 * This function calls the model to de-compress maze
	 * @param args[0] = inFile  
		      argv[1] = newMazeName
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{		
		String[] arr = args.split(" ", 2);
		
		m_model.decompressMaze(arr[0], arr[1]);
	}
	
	/************ Member **********/
	Model m_model;
}
