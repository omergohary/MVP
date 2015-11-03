/**
 * @file CommonCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents the common details of all commands (abstract class)
 * 				
 * @date    24/09/2015
 */
package controller.commands;

import model.IModel;
import view.IView;

/**
 * The Class CommonCommand.
 */
public abstract class CommonCommand implements ICommand 
{
	/**
	 * Instantiates a new common command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public CommonCommand(IView view, IModel model) 
	{
		this.view = view;
		this.model = model;
	}
	
	/********* Members *********/
	
	/** The view. */
	IView view;
	
	/** The model. */
	IModel model;
}
