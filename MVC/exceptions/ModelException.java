/**
 * @file ModelException.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a model problem that throw an exception
 * 				
 * @date    02/10/2015
 */

package exceptions;

/**
 * The Class ModelException.
 */
public class ModelException extends MVCException 
{

	/**
	 * Instantiates a new model exception.
	 */
	public ModelException() 
	{
		super();
	}
	
	/**
	 * Instantiates a new model exception.
	 *
	 * @param message the message
	 */
	public ModelException(String message) 
	{
		super(message);
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5700105840243867262L;
}
