/**
 * @file displayCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a created maze
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

/**
 * The command that responsible on "display3dMaze" request
 */
public class DisplayCommand extends CommonCommand 
{

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public DisplayCommand(IView view, IModel model) 
	{
		super(view, model);
	}

	/**
	 * This function is responsible to get the maze from the model and ask the view to print it out
	 * @param args -the maze's name to display
	 */
	@Override
	public void doCommand(String... args)
	{
		try 
		{
			model.displayMaze(args[0]);
		} 
		catch (ModelException e) 
		{
			view.display(new MyDisplayable(e.getMessage()));
		}
	}

}
