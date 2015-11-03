/**
 * @file MVC Exception.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a MVC problem that throw an exception
 * 				
 * @date    02/10/2015
 */

package exceptions;

/**
 * The Class MVCException.
 */
public class MVCException extends GenericException 
{

	/**
	 * Instantiates a new MVC exception.
	 */
	public MVCException() 
	{
		super();
	}
	
	/**
	 * Instantiates a new MVC exception.
	 *
	 * @param message the message
	 */
	public MVCException(String message) 
	{
		super(message);
	}
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1430620752233625578L;

}
