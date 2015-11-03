/**
 * @file ViewException.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a view problem that throw an exception
 * 				
 * @date    02/10/2015
 */

package exceptions;

/**
 * The Class ViewException.
 */
public class ViewException extends MVCException 
{
	/**
	 * Instantiates a new view exception.
	 */
	public ViewException() 
	{
		super();
	}
	
	/**
	 * Instantiates a new view exception.
	 *
	 * @param message the message
	 */
	public ViewException(String message) 
	{
		super(message);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3600902299661917326L;
}
