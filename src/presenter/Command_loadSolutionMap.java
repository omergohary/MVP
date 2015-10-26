/**
 * @file Command_loadSolutionMap.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              load a zipped solution map from file system
 * 				
 * @date    25/10/2015
 */

package presenter;

import java.io.File;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

/**
 * The command that responsible on "loadSolutionMap" request
 */
public class Command_loadSolutionMap implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_loadSolutionMap(Model model, View view)
	{
		m_model = model;
		m_view = view;
	}

	/**
	 * This function ask the model to load the solutions map from file system
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{
		m_model.LoadSolutionMap(args);
	}
	
	/************ Member **********/
	Model m_model;
	View m_view;
}
