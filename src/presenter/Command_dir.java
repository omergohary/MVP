/**
 * @file Command_dir.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              show the folders and files in specific dir the user typed.
 * 				
 * @date    24/09/2015
 */

package presenter;

import java.io.File;
import java.io.IOException;

/**
 * The command that responsible on "dir" request
 */
public class Command_dir implements Command
{

	/**
	 * This function is responsible to show the current dir (folders and files)
	 * @param args - the required user's dir
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{
		File dir = new File(args);

		File[] files = dir.listFiles();
		for (File file : files) 
		{
			if (file.isDirectory()) 
			{
				System.out.print("Dir:");
			} 
			else
			{
				System.out.print("      File:");
			}
			
			System.out.println(file.getCanonicalPath());
		}	
	}
}
