/**
 * @file Command_saveMaze.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to calculate file size
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
 * The command that responsible on "fileSize" request
 */
public class Command_fileSize implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_fileSize(Model model, View view)
	{
		m_model = model;
		m_view  = view;
	}

	/**
	 * This function calls the model to print file's size
	 * @param args - maze name
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{				
		m_model.printFileSize(args);
	}
	
	/************ Member **********/
	Model m_model;
	View m_view;
}
