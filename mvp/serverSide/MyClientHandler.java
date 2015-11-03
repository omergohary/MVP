/**
 * @file MyClientHandler.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the class that handle client that connected to the server Side
 * 				
 * @date    15/10/2015
 */

package serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import demo.Maze3dSearchable;
//import demo.Maze3dAdapter;
import exceptions.ModelException;
//import search.AStarSearcher;
import search.AstarSearcher;
//import search.BFS;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;
//import search.maze3d_AirHeuristic;
//import search.maze3d_ManhattanHeuristic;
import search.Searcher;
import search.Solution;

public class MyClientHandler implements ClientHandler 
{

	HashMap<String, Searcher<Position>> algorithmMap;
	ExecutorService threadPool;

	public MyClientHandler()
	{
		threadPool = Executors.newFixedThreadPool(1);
		algorithmMap = new HashMap<>();
		initAlgorithmMap();
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) 
	{
		BufferedReader readerFromClient = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter writerToClient = new PrintWriter(outToClient);
		ObjectInputStream mazeFromClient;
		ObjectOutputStream solutionToClient;
		Solution<Position> solution;
		String algorithmName = null;

		try 
		{
			algorithmName = readerFromClient.readLine();

			final Searcher<Position> searcher = getAlgorithm(algorithmName);
			
			if (searcher != null) 
			{
				writerToClient.println("alg");
				writerToClient.flush();

				mazeFromClient = new ObjectInputStream(inFromClient);
				final Maze3d maze = (Maze3d) mazeFromClient.readObject();
				Future<Solution<Position>> futureSolution = threadPool.submit(new Callable<Solution<Position>>() 
				{
					@Override
					public Solution<Position> call() throws Exception 
					{
						return searcher.search(new Maze3dSearchable(maze)); //Maze3dAdapter
					}
				});
				solution = futureSolution.get();

				solutionToClient = new ObjectOutputStream(outToClient);
				solutionToClient.writeObject(solution);
				solutionToClient.flush();
			}
		} 
		catch (IOException | ClassNotFoundException | InterruptedException | ExecutionException | ModelException e) 
		{
			
		}
	}

	private Searcher<Position> getAlgorithm(String algorithmName) throws ModelException 
	{
		Searcher<Position> algorithm;

		algorithm = validateName(algorithmName) ? algorithmMap.get(algorithmName) : null;

		return algorithm;
	}

	private boolean validateName(String name) 
	{
		return name != null && !name.isEmpty();
	}

	private void initAlgorithmMap() 
	{
		/*algorithmMap.put("bfs", new BFS<Position>());
		algorithmMap.put("manhattan", new AStarSearcher<Position>(new maze3d_ManhattanHeuristic()));
		algorithmMap.put("air", new AStarSearcher<Position>(new maze3d_AirHeuristic()));*/
		algorithmMap.put("bfs", new BFSSearcher<Position>());
		algorithmMap.put("manhattan", new AstarSearcher<Position>(new MazeManhattanHeuristic()));
		algorithmMap.put("air", new AstarSearcher<Position>(new MazeAirHeuristic()));

	}
}
