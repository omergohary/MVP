/**
 * @file ControllerException.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a controller problem that throw an exception
 * 				
 * @date    02/10/2015
 */

package exceptions;

/**
 * The Class ControllerException.
 */
public class ControllerException extends MVCException 
{
	/**
	 * Instantiates a new controller exception.
	 */
	public ControllerException() 
	{
		super();
	}

	/**
	 * Instantiates a new controller exception.
	 *
	 * @param message the message
	 */
	public ControllerException(String message) 
	{
		super(message);
	}
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4612947276551704216L;
}
