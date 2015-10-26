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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
import algorithms.search.State;
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
		executor 		= (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM_OF_THREADS);
	}
	
	@Override	
	public void generate3dMaze(final String mazeName, final int dimX, final int dimY, final int dimZ) throws IOException, InterruptedException, ExecutionException
	{
		if (m_mazes.containsKey(mazeName) == false) // caching
		{
			// ask thread pool to run this callback in additional thread
			Future<Maze3d> mazeInGeneration = executor.submit(new Generate3dMazeCallable(dimX, dimY, dimZ));
			
			Maze3d generatedMaze = mazeInGeneration.get();
			 
			// save it
			m_mazes.put(mazeName, generatedMaze);
		}	  
			
			// send a notification about finish generating to the presenter (the observer of this class)
			setChanged(); 
			notifyObservers("TheRequiredMazeIsReady " + "- " + mazeName);
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
	public void solveMaze(final String mazeName, final String algorithm) throws InterruptedException, ExecutionException 
	{
		if (m_solutions.containsKey(mazeName) == false) // caching
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
			
			// ask thread pool to run this callback in additional thread
			Future<Solution<Position>> solutionInProcess = executor.submit(new Solve3dMazeCallable(algorithm, maze3dAdapter));
			
			Solution<Position> solution = solutionInProcess.get();
									
			m_solutions.put(mazeName, solution);
		}

		// send a notification about finish solving to the presenter (the observer of this class)
		setChanged(); 
		notifyObservers("TheSolutionIsReady "+ "- " + mazeName + " (algorithm: " + algorithm + ")");  
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
	
	@Override
	public void SaveSolutionMap(String outFileName) throws IOException 
	{
		// Convert map to byte array
		StringBuffer serializeMap = new StringBuffer();
			
		for (int index = 0; index < m_solutions.size(); index++)
		{
			String mazeName = (String) m_solutions.keySet().toArray()[index];
			Solution<Position> solution = m_solutions.get(mazeName);
			
			serializeMap.append(mazeName);
			serializeMap.append("-");
			
			for (int positionsInSolution = 0; 
					 positionsInSolution < solution.size();
					 positionsInSolution ++)
			{
				Position newPosition = solution.getStateByIndex(positionsInSolution).getState();
				serializeMap.append(newPosition.getX() + ",");
		   	    serializeMap.append(newPosition.getY() + ",");
		   	    serializeMap.append(newPosition.getZ() + " ");
			}
			serializeMap.append("#"); // separated between solutions
		}
		
		FileOutputStream out    = new FileOutputStream(outFileName);
	    GZIPOutputStream zipOut = new GZIPOutputStream(out);


	    zipOut.write(String.valueOf(serializeMap).getBytes(), 0, serializeMap.length());
	    zipOut.close();	
	}
	
	@Override
	public void LoadSolutionMap(String InZipFile) throws IOException
	{
		FileInputStream in = new FileInputStream(InZipFile);
		GZIPInputStream zipIn = new GZIPInputStream(in);
		
		byte arr[] = new byte[MAX_COMPRESSED_MAP_LEN];
		 
		zipIn.read(arr);
		String compressedSolutionsMap = new String(arr);
		
		int numOfSolutions = compressedSolutionsMap.length() - compressedSolutionsMap.replace("#", "").length();
		
		String mazeName 		 = "";
		String positionsTogether = "";
		int startCurrser   = compressedSolutionsMap.indexOf('-') + 1;
		int endCurrser     = compressedSolutionsMap.indexOf(" #");
		String solutionsBeforeHandle = compressedSolutionsMap;
		
		for(int solutions = 0; solutions < numOfSolutions; solutions++)
		{
			// parse name and position from the string
			mazeName 		    = solutionsBeforeHandle.substring(0, compressedSolutionsMap.indexOf('-'));
			positionsTogether   = solutionsBeforeHandle.substring(startCurrser,endCurrser);
			
			int numOfCoordinates = positionsTogether.length() - positionsTogether.replace(" ", "").length() + 1;   // single " " = 2 positions, therefore i add 1 to this value
			String[] coordinates = positionsTogether.split(" ");
			
			Solution<Position> solutionToPush = new Solution<>();
			
			for (int index = 0; index < numOfCoordinates; index++)
			{
				String[] valuesInCoordinate = coordinates[index].split(",");
				
				State<Position> coordinateToPush = new State<Position>(new Position(Integer.valueOf(valuesInCoordinate[0]),
																					Integer.valueOf(valuesInCoordinate[1]),
																					Integer.valueOf(valuesInCoordinate[2])));
				
				solutionToPush.addState(coordinateToPush);
				
			}
			
			m_solutions.put(mazeName, solutionToPush);
			
			// update params to the next loop. Prevent this update if it is the last loop (index out of range)
			if (solutions + 1 < numOfSolutions)
			{
				startCurrser          = endCurrser + 2;
				solutionsBeforeHandle = solutionsBeforeHandle.substring(startCurrser, compressedSolutionsMap.indexOf(" #", startCurrser));
				endCurrser 			  = solutionsBeforeHandle.indexOf(" #");
			}
			
		}
		zipIn.close();
		
		setChanged(); 
		notifyObservers("LoadSolutionMapFinishSuccessfully ");
	} 
	
	/********************* Members **********************/	
	
	public static final int NUM_OF_THREADS = 20;
	
	public static final int MAX_COMPRESSED_MAP_LEN = 1000;
	
	HashMap<String, Maze3d> m_mazes;
	
	HashMap<String, Solution<Position>> m_solutions;
	
	HashMap<String, Integer> m_mazesSizes;
	
	HashMap<String, Integer> m_fileSizes;
	
    ThreadPoolExecutor executor;

}
