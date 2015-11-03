/**
 * @file SolveMazeCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to solve a maze
 * 				
 * @date    24/09/2015
 */


package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

/**
 * The command that responsible on "solve" request
 */
public class SolveCommand extends CommonCommand {

	/**
	 * Instantiates a new solve command.
	 *
	 * @param view
	 *            the view
	 * @param model
	 *            the model
	 */
	public SolveCommand(IView view, IModel model) 
	{
		super(view, model);
	}
	
	/**
	 * This function calls the model to solve the maze
	 * @param args[0] = mazeName  
	 * 	  	  argv[1] = algorithm  - BFS / AirDistance / Manhattan
	 */
	@Override
	public void doCommand(String... args) 
	{
		String[] splitted = args[0].split(" ");
		String mazeName = splitted[0];
		String algorithmName = splitted[1];
		String[] positions = null;

		if (splitted.length == 5) 
		{
			positions = new String[3];
			for (int i = 0; i < 3; i++) 
			{
				positions[i] = splitted[2 + i];
			}
		}

		try 
		{
			model.solve(mazeName, algorithmName, positions);
		} 
		catch (ModelException e) 
		{
			
		}
	}
}
