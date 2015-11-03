/**
 * @file GetPositionCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              return the position of the maze
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

public class GetPositionsCommand extends CommonCommand 
{

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view - the reference to the view in MVC
	 */
	public GetPositionsCommand(IView view, IModel model)
	{
		super(view, model);
	}

	@Override
	public void doCommand(String... args) 
	{
		try 
		{
			model.getPositions(args[0]);
		} 
		catch (ModelException e)
		{
		}
	}

}
