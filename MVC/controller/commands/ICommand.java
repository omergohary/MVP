/**
 * @file ICommand.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface to the commands
 * 				
 * @date    24/09/2015
 */

package controller.commands;

/**
 * The Interface ICommand.
 */
public interface ICommand 
{	
	/**
	 * Do command (pure func)
	 *
	 * @param args - the argv
	 */
	void doCommand(String... args);
}
