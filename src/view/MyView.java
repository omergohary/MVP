/**
 * @file MyView.java
 * 
 * @author Omer Gohary
 * 
 * @description This file implements the view interface
 * 				
 * @date    24/09/2015
 */

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Presenter;
import view.View;

public class MyView extends Observable implements View 
{
	/**
	 * C-Tor. Allocate the hash map
	 */
	public MyView(BufferedReader in, PrintWriter out)
	{
		m_commandContainer = null;
		m_isInitialized    = false;
		m_streamIn  	   = in;
		m_streamOut 	   = out;
	}

	@Override
	public void start() throws IOException 
	{
		if (m_isInitialized != true)
		{
			m_streamOut.println("MyView had not got its hash map from the presenter yet!");
			m_streamOut.flush();
			return;
		}
		
		try 
		{
			m_cli = new CLI(m_streamIn, m_streamOut, m_commandContainer);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// run CLI
		m_cli.Start();
	}
	
	@Override
	public void SetCommandsContainer(HashMap<String, Command> mapToSet)
	{
		m_commandContainer = mapToSet;
		m_isInitialized    = true;
	}
	
	@Override
	public void Print(String stringToPrint) 
	{
		m_streamOut.println(stringToPrint);
		m_streamOut.flush();
	}
	
	@Override
	public void display3dMaze(Maze3d maze3dToDisplay) 
	{
		maze3dToDisplay.printMaze();
		m_streamOut.flush();
	}
	
	@Override
	public void display2dMaze(int[][] maze2dToDisplay) 
	{
		for (int[] line : maze2dToDisplay)
		{
			for (int single : line)
			{
				m_streamOut.print(single);
			}
			m_streamOut.print("\n");
		}
		m_streamOut.print("\n");
		m_streamOut.flush();
	}

	@Override
	public void printSolution(Solution<Position> solutionToPrint)
	{
		solutionToPrint.printSolution();
		m_streamOut.flush();
	}
	
	@Override
	public void shutDown() throws IOException 
	{
		m_streamOut.println("Bye Bye");
		m_streamOut.flush();
		m_cli.shutDown();
		m_streamIn.close();
		m_streamOut.close();
	}
	
	/********************* Members *******************/
	
	
	/** Streams - in and out **/
	BufferedReader m_streamIn;
	PrintWriter    m_streamOut;
	
	boolean m_isInitialized;
	HashMap<String, Command> m_commandContainer;
	CLI m_cli;
}
