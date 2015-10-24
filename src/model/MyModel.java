/**
 * @file MyModel.java
 * 
 * @author Omer Gohary
 * 
 * @description This file implements the model interface
 * 				
 * @date    24/09/2015
 */

package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import algorithms.demo.Maze3dAdapter;
import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.AStarSearcher;
import algorithms.search.BFS;
import algorithms.search.Solution;
import algorithms.search.maze3d_AirHeuristic;
import algorithms.search.maze3d_ManhattanHeuristic;
import presenter.Command;
import presenter.Presenter;
import model.Model;

public class MyModel implements Model 
{
	/** C-Tor **/
	public MyModel()
	{
		m_mazes     = new HashMap<String, Maze3d>();
		m_solutions = new HashMap<String, Solution<Position>>();
	}
	
	@Override
	public void setPresenter(Presenter presenterToCommunicate)
	{
		m_presenter = presenterToCommunicate;
	}
	
	@Override	
	public void generate3dMaze(final String mazeName, final int dimX, final int dimY, final int dimZ) throws IOException
	{
		  new Thread(new Runnable() 
		  {
			  @Override
			  public void run() 
			  {
				  // generate the maze
				  Maze3d generatedMaze = new SimpleMaze3dGenerator().generate(dimX, dimY, dimZ);
				  
				  // save it
				  m_mazes.put(mazeName, generatedMaze);
				  
				  // ask presenter to send msg out
				  String msgToPrint = String.format("Maze: %s is ready!", mazeName);
				  m_presenter.Print(msgToPrint);
		      }
			  
		  }).start();
	}
	
	@Override
	public Maze3d getSaved3dMaze(String mazeName) 
	{
		Maze3d mazeToReturn = m_mazes.get(mazeName);
		
		if ( mazeToReturn == null)
		{
			m_presenter.Print("The required maze is not exist");
		}
		
		return mazeToReturn;
	}
	
	@Override
	public void compressMaze(String mazeName, String fileName) throws IOException 
	{
		Maze3d mazeToCompress = m_mazes.get(mazeName);
		
		if ( mazeToCompress == null)
		{
			m_presenter.Print("The required maze is not exist");
			return;
		}
		
		OutputStream out;
		try 
		{
			out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(mazeToCompress.toByteArray());
			out.flush();
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void decompressMaze(String fileToRead, String mazeNewName) throws FileNotFoundException, IOException 
	{
		InputStream in = new MyDecompressorInputStream (new FileInputStream(fileToRead));
		
		byte b[]=new byte[1000000]; // buffer to read from file
		
		in.read(b);
		
		in.close();
		
		Maze3d loaded = new Maze3d(b);
		
		m_mazes.put(mazeNewName, loaded);
	}
	
	@Override
	public void printMazeSize(String mazeToCheck) 
	{
		
		Maze3d checkedMaze = m_mazes.get(mazeToCheck);
		
		if (checkedMaze == null)
		{
			m_presenter.Print("The required maze is not exist");
			return;
		}
		
		int size = checkedMaze.getDimX() * checkedMaze.getDimY() * checkedMaze.getDimZ();
		
		String msgToPrint = String.format("Maze: %s ,Size %d", mazeToCheck, size);
		
		m_presenter.Print(msgToPrint);
	}

	@Override
	public void printFileSize(String mazeToCheck) 
	{
		Maze3d checkedMaze = m_mazes.get(mazeToCheck);
		
		if (checkedMaze == null)
		{
			m_presenter.Print("The required maze is not exist");
			return;
		}
		
		OutputStream out;
		
		try 
		{
			// Compress it
			out = new MyCompressorOutputStream(new FileOutputStream("CalculateFileSize.txt"));
			out.write(checkedMaze.toByteArray());
			out.flush();
			out.close();
			
			// check the len
			File checkFile = new File("./CalculateFileSize.txt");
			String msgToPrint = String.format("Maze: %s ,Size %d", mazeToCheck, checkFile.length());
			m_presenter.Print(msgToPrint);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}	
	
	@Override
	public void solveMaze(final String mazeName, final String algorithm) 
	{
		
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				Maze3d mazeToSolve = m_mazes.get(mazeName);
				
				if (mazeToSolve == null)
				{
					m_presenter.Print("The required maze is not exist");
					return;
				}
				
				Maze3dAdapter maze3dAdapter = new Maze3dAdapter(mazeToSolve);
										
				switch(algorithm)
				{
					case("BFS"):
					case("bfs"):
					{
						// save the solution
						m_solutions.put(mazeName, new BFS<Position>().search(maze3dAdapter));
						break;
					}
					
					case("AirDistance"):
					{
						m_solutions.put(mazeName, new AStarSearcher<Position>(new maze3d_AirHeuristic()).search(maze3dAdapter));
						break;
					}
					
					case("Manhattan"):
					{
						m_solutions.put(mazeName, new AStarSearcher<Position>(new maze3d_ManhattanHeuristic()).search(maze3dAdapter));
						break;
					}
					
					default:
					{
						System.out.println("Bad type of algorithm!");
						return;
					}
				}
				 
				String msgToPrint = String.format("Solution for the maze- %s (algorithm: %s) is ready!", mazeName, algorithm);
				m_presenter.Print(msgToPrint);
				
	      }
		  
	  }).start();
		  
	}
	
	@Override
	public Solution<Position> getSolution(String mazeName) 
	{
		Solution<Position> solutionToPrint = m_solutions.get(mazeName);
		
		if (solutionToPrint == null)
		{
			m_presenter.Print("The required solution is not exist");
			return null;
		}
		
		return solutionToPrint;
	}
	
	/********************* Members **********************/
	Presenter m_presenter;
	
	HashMap<String, Maze3d> m_mazes;
	
	HashMap<String, Solution<Position>> m_solutions;

}
