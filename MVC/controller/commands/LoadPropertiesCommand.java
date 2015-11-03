/**
 * @file loadPropertiesCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              load new properties from file
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import model.IModel;
import view.IView;

public class LoadPropertiesCommand extends CommonCommand 
{
	/**
	 * Instantiates a new load maze command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public LoadPropertiesCommand(IView view, IModel model)
	{
		super(view, model);
	}

	@Override
	public void doCommand(String... args)
	{
		model.loadProperties(args[0]);
	}

}
