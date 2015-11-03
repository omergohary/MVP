/**
 * @file MazeSizeCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to calculate the size of specific maze
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

/**
 * The command that responsible on "saveMaze" request
 */
public class MazeSizeCommand extends CommonCommand {

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public MazeSizeCommand(IView view, IModel model) {
		super(view, model);
	}

	/**
	 * This function calls the model to print maze's size
	 * @param args - the maze's name
	 */
	@Override
	public void doCommand(String... args) {
		try {
			model.mazeSize(args);
		} catch (ModelException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}
	}

}
