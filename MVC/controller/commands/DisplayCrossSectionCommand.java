/**
 * @file displayCrossSectionCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a specific cross section of specific maze
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import exceptions.MVCException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

/**
 * The command that responsible on "displayCrossSection" request
 */
public class DisplayCrossSectionCommand extends CommonCommand {

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public DisplayCrossSectionCommand(IView view, IModel model) 
	{
		super(view, model);
	}
	/**
	 * This function calls the model to get a 3d maze, takes the cross section, and sends to the view to print
	 * @param args[0] = mazeName
	 * 	      argv[1] =  X/Y/Z  
	 * 	      args[2] = index
	 */
	@Override
	public void doCommand(String... args) 
	{
		try 
		{
			model.displayCrossSection(args);
		} 
		catch (MVCException e) 
		{
			view.display(new MyDisplayable(e.getMessage()));
		}
	}
}
