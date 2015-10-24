/**
 * @file Presenter.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface to the Presenter in the MVP
 * 				
 * @date    09/10/2015
 */

package presenter;

public interface Presenter
{
	/**
	 * This function gives an access to print to the outStream
	 * 
	 * @param stringToPrint - the string to print
	 */
	public void Print(String stringToPrint);
}
