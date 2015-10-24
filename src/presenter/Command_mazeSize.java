/**
 * @file Command_mazeSize.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to calculate the size of specific maze
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
public class Command_mazeSize implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_mazeSize(Model model, View view)
	{
		m_model = model;
		m_view  = view;
	}


	/**
	 * This function calls the model to print maze's size
	 * @param args - the maze's name
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{			
		m_model.printMazeSize(args);
	}
	
	/************ Member **********/
	Model m_model;
	View m_view;
}
