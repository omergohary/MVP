/**
 * @file CommonView.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is abstract file of the view in our design
 * 				
 * @date    24/09/2015
 */

package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

/**
 * The Class CommonView.
 */
public abstract class CommonView implements MVCView 
{
		
	@Override
	public void setController(IController aController) 
	{
		this.controller = aController;
	}
	

	@Override
	public void setStringCommandMap(HashMap<String, ICommand> map) 
	{
		this.map=map;
		cli = new CLI(in, out, map);
	}


	@Override
	public void start() 
	{
		cli.start();
	}
	
	/** The in. */
	InputStream in;
	
	/** The out. */
	OutputStream out;
	
	/** The controller. */
	IController controller;
	
	/** The map. */
	HashMap<String,ICommand> map;
	
	/** The cli. */
	CLI cli;
}
