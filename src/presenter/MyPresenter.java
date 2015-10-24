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
	
	@Override
	public void Print(String stringToPrint) 
	{
		m_view.Print(stringToPrint);
	}
	
	/*********************** Member **********************/
	
	View  m_view;
	Model m_model;
}
