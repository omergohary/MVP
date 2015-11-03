/**
 * @file GenerateMaze3dCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to create new 3d maze
 * 				
 * @date    24/09/2015
 */

package controller.commands;

import java.util.ArrayList;
import java.util.List;

import exceptions.ModelException;
import model.IModel;
import view.IView;

/**
 * The command that responsible on "generate3dMaze" request
 */
public class GenerateMaze3dCommand extends CommonCommand {

	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view - the reference to the view in MVC
	 */
	public GenerateMaze3dCommand(IView view, IModel model) 
	{
		super(view, model);
	}

	/**
	 * This function calls the model to generate new 3d maze
	 * @param args[0] = name
		   	  args[1] = dimX
		   	  args[2] = dimY
		   	  args[3] = dimZ
	 */
	@Override
	public void doCommand(String... args) 
	{
		String[] splitted = args[0].split(" ");
		String mazeName = splitted[0];
		ArrayList<String> argu = new ArrayList<>();
		
		for(int i=1; i<splitted.length; i++) 
		{
			argu.add(splitted[i]);
		}
		String arguments = join(argu, " ");

		try 
		{
			model.generateMaze3d(mazeName, arguments);
		} 
		catch (ModelException e) 
		{
			
		}
	}
	
	// private helper function to join strings for the last function
	public static String join(List<String> strings, String separator)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < strings.size(); i++)
		{
			sb.append(strings.get(i));
			if(i < strings.size() - 1)
				sb.append(separator);
		}
		return sb.toString();				
	}
}
