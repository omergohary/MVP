/**
 * @file ExitCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a solution for specific maze
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import model.IModel;
import view.IView;

/**
 * The command that responsible on "exit" request
 */
public class ExitCommand extends CommonCommand 
{

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public ExitCommand(IView view, IModel model) 
	{
		super(view, model);
	}

	/**
	 * This function is responsible to close all files and threads safely.
	 * @param args - nothing
	 */
	@Override
	public void doCommand(String... args) {
		model.exit();
	}
}
