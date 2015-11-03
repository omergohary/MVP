/**
 * @file DisplaySolutionCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a solution for specific maze
 * 				
 * @date    24/09/2015
 */


package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

/**
 * The command that responsible on "displaySolution" request
 */
public class DisplaySolutionCommand extends CommonCommand {

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public DisplaySolutionCommand(IView view, IModel model) {
		super(view, model);
	}


	/**
	 * This function calls the model give a solution, and sends to the view to print
	 * @param args - maze name
	 */
	@Override
	public void doCommand(String... args) {
		try {
			model.displaySolution(args);
		} catch (ModelException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}
	}
}
