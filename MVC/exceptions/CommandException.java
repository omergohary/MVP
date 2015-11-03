/**
 * @file CommandException.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command problem that throw an exection
 * 				
 * @date    02/10/2015
 */

package exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandException.
 */
public class CommandException extends ControllerException 
{
	
	/**
	 * Instantiates a new command exception.
	 */
	public CommandException() 
	{
		super();
	}

	/**
	 * Instantiates a new command exception.
	 *
	 * @param message the message
	 */
	public CommandException(String message) 
	{
		super(message);
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3238722405844916645L;
}
