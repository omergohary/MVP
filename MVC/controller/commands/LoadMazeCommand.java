/**
 * @file loadMazeCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to de-compress maze from file and insert to the saved mazes list
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

/**
 * The command that responsible on "loadMaze" request
 */
public class LoadMazeCommand extends CommonCommand {

	/**
	 * Instantiates a new load maze command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public LoadMazeCommand(IView view, IModel model) {
		super(view, model);
	}

	/**
	 * This function calls the model to de-compress maze
	 * @param args[0] = inFile  
		      argv[1] = newMazeName
	 */
	@Override
	public void doCommand(String... args) {
		// IO Command
		String[] splitted = args[0].split(" ");
		
		try {
			model.loadMaze(splitted[0], splitted[1]);
		} catch (ModelException e) {}

	}

}
