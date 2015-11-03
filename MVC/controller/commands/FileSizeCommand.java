/**
 * @file FileSizeCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to calculate file size
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

/**
 * The command that responsible on "fileSize" request
 */
public class FileSizeCommand extends CommonCommand 
{

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public FileSizeCommand(IView view, IModel model)
	{
		super(view, model);
	}


	/**
	 * This function calls the model to print file's size
	 * @param args - maze name
	 */
	@Override
	public void doCommand(String... args) 
	{
		// IO Command

		String fileName = args[0];
		try 
		{
			model.getFileSize(fileName);
		} 
		catch (ModelException e) 
		{
			
		}
	}
}
