/**
 * @file SaveMazeCommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to compress an exist maze and save it to file
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
 * The command that responsible on "saveMaze" request
 */
public class SaveMazeCommand extends CommonCommand {

	/**
	 * Instantiates a new save maze command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public SaveMazeCommand(IView view, IModel model) {
		super(view, model);
	}

	/**
	 * This function calls the model to compress a maze
	 * @param args[0] = mazeName  
			  argv[1] = outFile
	 */
	@Override
	public void doCommand(String... args) 
	{
		// IO Command
		String[] splitted = args[0].split(" ");
		String fileName = splitted[splitted.length-1];
		
		ArrayList<String> buffer = new ArrayList<>();
		for(int i=0;i<splitted.length-1;i++) 
		{
			buffer.add(splitted[i]);
		}
		
		String mazeName = join(buffer, " ");
		
		try 
		{
			model.saveMaze(mazeName, fileName);
		} 
		catch (ModelException e) 
		{
			
		}
	}
	
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
