/**
 * @file GenerateException.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a generate problem that throw an exception
 * 				
 * @date    02/10/2015
 */

package exceptions;

/**
 * The Class GenerateException.
 */
public class GenerateException extends CommandException 
{

	/**
	 * Instantiates a new generate exception.
	 */
	public GenerateException()
	{
		super();
	}
	
	/**
	 * Instantiates a new generate exception.
	 *
	 * @param message the message
	 */
	public GenerateException(String message) 
	{
		super(message);
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8171288507071158527L;
}
