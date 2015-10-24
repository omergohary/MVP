/**
 * @file Command_display3dMaze.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a created maze
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
 * The command that responsible on "display3dMaze" request
 */
public class Command_display3dMaze implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_display3dMaze(Model model, View view)
	{
		m_model = model;
		m_view  = view;
	}

	/**
	 * This function is responsible to get the maze from the model and ask the view to print it out
	 * @param args -the maze's name to display
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{
		/* in this case:  args[0] = name */
				
		 Maze3d mazeToDisplay = m_model.getSaved3dMaze(args);
		 m_view.display3dMaze(mazeToDisplay);
	}
	
	/************ Member **********/
	Model m_model;
	View m_view;
}

