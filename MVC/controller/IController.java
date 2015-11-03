/**
 * @file IController.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface to the controller in the MVC
 * 				
 * @date    24/09/2015
 */


package controller;

import view.IDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Interface IController.
 */
public interface IController 
{

	/**
	 * This function gives an access to display to the outStream
	 * 
	 * @param displayable - the displayable instance
	 */
	void display(IDisplayable displayable);
	
}
