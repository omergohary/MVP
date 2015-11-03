/**
 * @file IDisplayable.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is an interface of displayable object
 * 				
 * @date    24/09/2015
 */


package view;

import java.io.OutputStream;

/**
 * The Interface IDisplayable.
 */
public interface IDisplayable 
{
	
	/**
	 * Display.
	 *
	 * @param out the out
	 */
	void display(OutputStream out);

	void setMessage(String aMessage);

	String getMessage();
}
