/**
 * @file MoveDirectionCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to moving the directions in maze3d
 * 				
 * @date    24/09/2015
 */


package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

public class MoveDirectionCommand extends CommonCommand
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public MoveDirectionCommand(IView view, IModel model) 
	{
		super(view, model);
	}

	@Override
	public void doCommand(String... args)
	{
		String[] splitted = args[0].split(" ");
		String direction = splitted[0];
		String mazeName = splitted[1];
		String position = splitted[2] + " " + splitted[3] + " " + splitted[4];
		try 
		{
			model.moveDirection(mazeName, position, direction);
		}
		catch (ModelException e) 
		{
			
		}
	}

}
