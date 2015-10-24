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
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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

public class MyModel extends Observable implements Model 
{
	/** C-Tor **/
	public MyModel()
	{
		m_mazes     	= new HashMap<String, Maze3d>();
		m_solutions 	= new HashMap<String, Solution<Position>>();
		m_mazesSizes    = new HashMap<String, Integer>();
		m_fileSizes     = new HashMap<String, Integer>();
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
				  
				  // send a notification about finish generating to the presenter (the observer of this class)
				  setChanged(); 
				  notifyObservers("TheRequiredMazeIsReady " + "- " + mazeName);
			  }
			  
		  }).start();
	}
	
	@Override
	public Maze3d getSaved3dMaze(String mazeName) 
	{
		Maze3d mazeToReturn = m_mazes.get(mazeName);
		
		if ( mazeToReturn == null)
		{
			  // send a notification about unknown maze
			  setChanged(); 
			  notifyObservers("TheRequiredMazeIsNotExist " + "- " + mazeName);
			  return null;
		}
		
		return mazeToReturn;
	}
	
	@Override
	public void compressMaze(String mazeName, String fileName) throws IOException 
	{
		Maze3d mazeToCompress = m_mazes.get(mazeName);
		
		if ( mazeToCompress == null)
		{
			  // send a notification about unknown maze
			  setChanged(); 
			  notifyObservers("TheRequiredMazeIsNotExist " + "- " + mazeName);
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
	public void calculateMazeSize(String mazeToCheck) 
	{
		Maze3d checkedMaze = m_mazes.get(mazeToCheck);
		
		if (checkedMaze == null)
		{
			// send a notification about unknown maze
			setChanged(); 
			notifyObservers("TheRequiredMazeIsNotExist " + "- " + mazeToCheck);
			return;
		}
		
		int size = checkedMaze.getDimX() * checkedMaze.getDimY() * checkedMaze.getDimZ();
		m_mazesSizes.put(mazeToCheck, size);
		
		 // send a notification about finish calculating the required maze size
		 setChanged(); 
		 notifyObservers("TheRequiredMazeSizeIsReady " + mazeToCheck);
	}
	
	@Override
	public int getMazeSize(String mazeToCheck)
	{
		return m_mazesSizes.get(mazeToCheck);
	}
	
	@Override
	public void calculateFileSize(String mazeToCheck) 
	{
		Maze3d checkedMaze = m_mazes.get(mazeToCheck);
		
		if (checkedMaze == null)
		{
			// send a notification about unknown maze
			setChanged(); 
			notifyObservers("TheRequiredMazeIsNotExist "+ "- " + mazeToCheck);
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
			int fileSize = (int)checkFile.length();
			m_fileSizes.put(mazeToCheck, fileSize);
			
			// send a notification about finish calculating the required maze size
			setChanged(); 
			notifyObservers("TheRequiredFileSizeIsReady " + mazeToCheck);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}	
	
	@Override
	public int getFileSize(String mazeToCheck)
	{
		return m_fileSizes.get(mazeToCheck);
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
					// send a notification about unknown maze
					setChanged(); 
					notifyObservers("TheRequiredMazeIsNotExist " + "- " + mazeName);
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
				 
				// send a notification about finish solving to the presenter (the observer of this class)
				setChanged(); 
				notifyObservers("TheSolutionIsReady "+ "- " + mazeName + " (algorithm: " + algorithm + ")");
				
	      }
		  
	  }).start();
		  
	}
	
	@Override
	public Solution<Position> getSolution(String mazeName) 
	{
		Solution<Position> solutionToPrint = m_solutions.get(mazeName);
		
		if (solutionToPrint == null)
		{
			// send a notification about unknown maze
			setChanged(); 
			notifyObservers("TheRequiredMazeIsNotExist " + "- " + mazeName);
			return null;
		}
		
		return solutionToPrint;
	}
	
	/********************* Members **********************/	
	
	public static final int NUM_OF_THREADS = 20;
	
	HashMap<String, Maze3d> m_mazes;
	
	HashMap<String, Solution<Position>> m_solutions;
	
	HashMap<String, Integer> m_mazesSizes;
	
	HashMap<String, Integer> m_fileSizes;
	
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_OF_THREADS);


}
