/**
 * @file GenericException.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a generic problem that throw an exception
 * 				
 * @date    02/10/2015
 */

package exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ColmanException.
 */
public class GenericException extends Exception 
{

	/**
	 * Instantiates a new generic exception.
	 */
	public GenericException() 
	{
		super();
	}
	
	/**
	 * Instantiates a new generic exception.
	 *
	 * @param message the message
	 */
	public GenericException(String message)
	{
		super(message);
	}
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1540034039967114158L;
}
