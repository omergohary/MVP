/**
 * @file MVCView.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is the an interface between the concrete class and the main interface - IView
 * 				
 * @date    24/09/2015
 */


package view;

import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

public interface MVCView extends IView 
{

	
	/**
	 * Sets the controller.
	 *
	 * @param aController the new controller
	 */
	public void setController(IController aController);
	
	/**
	 * Sets the string command map.
	 *
	 * @param map the map
	 */
	public void setStringCommandMap(HashMap<String,ICommand> map);
}
