/**
 * @file CommonDisplayable.java
 * 
 * @author Omer Gohary
 * 
 * @description This file contains the common functions of displayable object
 * 				
 * @date    24/09/2015
 */

package view;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * The Class CommonDisplayable.
 */
public abstract class CommonDisplayable implements IDisplayable 
{
	@Override
	public void display(OutputStream out) 
	{
		PrintWriter writer = new PrintWriter(out);
		writer.println(message);
		writer.flush();
	}

	/**
	 * Sets the message.
	 *
	 * @param aMessage the new message
	 */
	@Override
	public void setMessage(String aMessage) 
	{
		this.message = aMessage;
	}
	
	@Override
	public String getMessage() 
	{
		return this.message;
	}
	
	
	/** The message. */
	String message;
}
