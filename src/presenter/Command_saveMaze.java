/**
 * @file Command_saveMaze.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to compress an exist maze and save it to file
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
 * The command that responsible on "saveMaze" request
 */
public class Command_saveMaze implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 */
	public Command_saveMaze(Model model)
	{
		m_model = model;
	}

	/**
	 * This function calls the model to compress a maze
	 * @param args[0] = mazeName  
			  argv[1] = outFile
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{		
		String[] arr = args.split(" ", 2);
		
		m_model.compressMaze(arr[0], arr[1]);
	}
	
	/************ Member **********/
	Model m_model;
}
