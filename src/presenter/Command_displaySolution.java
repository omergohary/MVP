/**
 * @file Command_displayCrossSection.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a solution for specific maze
 * 				
 * @date    24/09/2015
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
 * The command that responsible on "displaySolution" request
 */
public class Command_displaySolution implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_displaySolution(Model model, View view)
	{
		m_model = model;
		m_view = view;
	}

	/**
	 * This function calls the model give a solution, and sends to the view to print
	 * @param args - maze name
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{
		Solution<Position> solution = m_model.getSolution(args);
		
		m_view.printSolution(solution);
	}
	
	/************ Member **********/
	Model m_model;
	View m_view;
}
