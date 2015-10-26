/**
 * @file Command_saveSolutionMap.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              save the hash map of maze 3d solutions
 * 				
 * @date    25/10/2015
 */

package presenter;

import java.io.File;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * The command that responsible on "saveSolutionMap" request
 */
public class Command_saveSolutionMap implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_saveSolutionMap(Model model)
	{
		m_model = model;
	}

	/**
	 * This function calls the model to save solutions map
	 * @param args - zip output file
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{				
		m_model.SaveSolutionMap(args);
	}
	
	/************ Member **********/
	Model m_model;
}
