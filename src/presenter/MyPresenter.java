/**
 * @file MyPresenter.java
 * 
 * @author Omer Gohary
 * 
 * @description This file implements the Presenter interface
 * 				
 * @date    24/09/2015
 */

package presenter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Observable;

import javax.activation.CommandMap;

import model.Model;
import view.View;

/**
 * This class implements the Presenter in the MVP architectural pattern
 *
 */
public class MyPresenter implements Presenter 
{
	/**
	 * C-tor
	 * @param view  - a reference to the view object
	 * @param model - a reference to the model object
	 */
	public MyPresenter(View view, Model model)
	{
		m_view  = view;
		m_model = model;
		
		/* Allocate all commands **/
		Command_dir dir 								= new Command_dir();
		Command_generateNew3dMaze generateNew3dMaze 	= new Command_generateNew3dMaze(m_model);
		Command_display3dMaze display3dMaze         	= new Command_display3dMaze(m_model, m_view);
		Command_displayCrossSection displayCrossSection = new Command_displayCrossSection(m_model, m_view);
		Command_saveMaze saveMaze                       = new Command_saveMaze(m_model);
		Command_loadMaze loadMaze                       = new Command_loadMaze(m_model);
		Command_mazeSize mazeSize                       = new Command_mazeSize(m_model, m_view);
		Command_fileSize fileSize                       = new Command_fileSize(m_model, m_view);
		Command_solve solve                             = new Command_solve(m_model);
		Command_displaySolution displaySolution         = new Command_displaySolution(m_model, m_view);
		Command_exit exit                               = new Command_exit(m_model, m_view);
		
		/* Allocate the hash map and push all commands and strings */
		HashMap<String, Command> commandMap = new HashMap<String,Command>();
		
		commandMap.put("dir", dir);
		commandMap.put("generate3dMaze", generateNew3dMaze);
		commandMap.put("display3dMaze", display3dMaze);
		commandMap.put("displayCrossSection", displayCrossSection);
		commandMap.put("saveMaze", saveMaze);
		commandMap.put("loadMaze", loadMaze);
		commandMap.put("mazeSize", mazeSize);
		commandMap.put("fileSize", fileSize);
		commandMap.put("solve", solve);
		commandMap.put("displaySolution", displaySolution);
		commandMap.put("exit", exit);
		
		// send to the view the hash-map
		m_view.SetCommandsContainer(commandMap);
	}
	
	/**
	 * A function that handle a notification from the model and view
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		
		String args = arg.toString();
		
		if (o == (Observable)(m_view))
		{
			// no notifications from view for now
		}
		
		else if (o == (Observable)(m_model))
		{
			switch(args.substring(0, args.indexOf(' ')))
			{
				// These cases represent notification to user
				case("TheRequiredMazeIsReady"):
				case("TheRequiredMazeIsNotExist"):
				case("TheSolutionIsReady"):
				{
					m_view.Print(args);
					break;
				}
				// a notification to the presenter to ask for maze's size
				case("TheRequiredMazeSizeIsReady"): // must have the maze name in arg[1]
				{
					int sizeOfMaze = m_model.getMazeSize(args.substring(args.indexOf(' ') + 1));
					String msgToPrint = String.format("Maze: %s, Size %d", args.substring(args.indexOf(' ') + 1), sizeOfMaze);
					m_view.Print(msgToPrint);
					break;
				}
				// a notification to the presenter to ask for file's size
				case("TheRequiredFileSizeIsReady"): // must have the maze name in arg[1]
				{
					int sizeOfFile = m_model.getFileSize(args.substring(args.indexOf(' ') + 1));
					String msgToPrint = String.format("Maze: %s, Size %d", args.substring(args.indexOf(' ') + 1), sizeOfFile);
					m_view.Print(msgToPrint);
					break;
				}
				default:
				{
					System.out.println("Bad type of notification from Model");
				}
			}
		}
			
	}
	
	@Override
	public void Print(String stringToPrint) 
	{
		m_view.Print(stringToPrint);
	}
	
	/*********************** Member **********************/
	
	View  m_view;
	Model  m_model;

}
