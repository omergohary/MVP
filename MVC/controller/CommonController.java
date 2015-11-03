/**
 * @file CommonController.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents the common logic of controller in MVC design
 * 				
 * @date    24/09/2015
 */

package controller;

import java.util.HashMap;

import controller.commands.DirCommand;
import controller.commands.DisplayCommand;
import controller.commands.DisplayCrossSectionCommand;
import controller.commands.DisplaySolutionCommand;
import controller.commands.ExitCommand;
import controller.commands.FileSizeCommand;
import controller.commands.GenerateMaze3dCommand;
import controller.commands.ICommand;
import controller.commands.LoadMazeCommand;
import controller.commands.MazeSizeCommand;
import controller.commands.SaveMazeCommand;
import controller.commands.SolveCommand;
import model.MVCModel;
import view.IDisplayable;
import view.MVCView;

/**
 * The Class CommonController.
 */
public abstract class CommonController implements IController 
{
		/**
	 * Instantiates a new common controller.
	 *
	 * @param aModel the a model
	 * @param aView the a view
	 */
	public CommonController(MVCModel aModel, MVCView aView) 
	{
		this.model = aModel;
		this.view = aView;
		
		model.setController(this);
		view.setController(this);
		
		map = new HashMap<>(12);
		initStringCommandMap();
		
		view.setStringCommandMap(map);
	}

	/**
	 * Inits the string command map.
	 */
	private void initStringCommandMap() {
		map.put("dir", new DirCommand(view, model));
		map.put("generate 3d maze", new GenerateMaze3dCommand(view, model));
		map.put("display", new DisplayCommand(view, model));
		map.put("display cross section by", new DisplayCrossSectionCommand(view, model));
		map.put("save maze", new SaveMazeCommand(view,model));
		map.put("load maze", new LoadMazeCommand(view, model));
		map.put("maze size", new MazeSizeCommand(view, model));
		map.put("file size", new FileSizeCommand(view, model));
		map.put("solve", new SolveCommand(view, model));
		map.put("display solution", new DisplaySolutionCommand(view, model));
		map.put("exit", new ExitCommand(view, model));
	}
	
	@Override
	public void display(IDisplayable displayable) 
	{
		view.display(displayable);
	}
	
	/******************** Members ***********/
	
	/** The model. */
	MVCModel model;
	
	/** The view. */
	MVCView view;
	
	/** The map. */
	HashMap<String,ICommand> map;
	
}
