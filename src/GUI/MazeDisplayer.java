/**
 * @file MazeDisplayer.java
 * 
 * @author colman
 * 
 * @description This file represents a class that responsible to display the maze window
 * 				
 * @date    28/10/2015
 */
package GUI;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas 
{
	public MazeDisplayer(Composite parent, int style) 
	{
		super(parent, style);
	}

	public void setMazeData(int[][] mazeData) 
	{
		this.m_mazeData = mazeData;
		redraw();
	}

	public abstract void setCharacterPosition(int row, int col);
	
	/************ Members ***********/
	int[][] m_mazeData;
}