/**
 * @file IView.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is the view interface in the MVC design
 * 				
 * @date    24/09/2015
 */

package view;

/**
 * The Interface IView.
 */
public interface IView 
{
	
	/**
	 * Display.
	 *
	 * @param displayable the displayable
	 */
	public void display(IDisplayable displayable);
	
	/**
	 * Start.
	 */
	public void start();

}
