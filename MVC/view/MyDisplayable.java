/**
 * @file MyDisplayable.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is an implementation to the displayable abstarct class
 * 				
 * @date    24/09/2015
 */

package view;

/**
 * The Class MyDisplayable.
 */
public class MyDisplayable extends CommonDisplayable
{
	
	/**
	 * Instantiates a new my displayable.
	 */
	public MyDisplayable() 
	{
		this.message = "";
	}
	
	/**
	 * Instantiates a new my displayable.
	 *
	 * @param text the text
	 */
	public MyDisplayable(String text) 
	{
		this.message = text;
	}
}
