/**
 * @file Command_displayCrossSection.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a command that responsible to 
 *              ask the model to display a specific cross section of specific maze
 * 				
 * @date    24/09/2015
 */

package presenter;

import java.io.File;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * The command that responsible on "displayCrossSection" request
 */
public class Command_displayCrossSection implements Command
{
	/**
	 * C-tor
	 * @param model - the reference to the model in MVC
	 * @param view  - the reference to the view in MVC
	 */
	public Command_displayCrossSection(Model model, View view)
	{
		m_model = model;
		m_view  = view;
	}

	/**
	 * This function calls the model to get a 3d maze, takes the cross section, and sends to the view to print
	 * @param args[0] = mazeName
	 * 	      argv[1] =  X/Y/Z  
	 * 	      args[2] = index
	 */
	@Override
	public void doCommand(String args) throws IOException 
	{
		String[] arr = args.split(" ", 3);
		
		 Maze3d mazeToDisplay = m_model.getSaved3dMaze(arr[0]);
		 
		 switch(arr[1])
		 {
		 	case("X"):
		 	case("x"):
		 	{
		 		m_view.display2dMaze(mazeToDisplay.getCrossSectionByX(Integer.parseInt(arr[2])));
		 		break;
		 	}
		 	
		 	case("Y"):
		 	case("y"):
		 	{
		 		m_view.display2dMaze(mazeToDisplay.getCrossSectionByY(Integer.parseInt(arr[2])));
		 		break;
		 	}
		 	
		 	case("Z"):
		 	case("z"):
		 	{
		 		m_view.display2dMaze(mazeToDisplay.getCrossSectionByZ(Integer.parseInt(arr[2])));
		 		break;
		 	}
		 	
		 	default:
		 	{
		 		System.out.println("Bad type of section - X/Y/Z only");
		 	}
		 }
	}
	
	/************ Member **********/
	Model m_model;
	View m_view;
}
