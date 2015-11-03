/**
 * @file Model_MVP .java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the abstract model in MVP design that implements the model generic interface
 * 				
 * @date    02/11/2015
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import exceptions.GenericException;
import exceptions.CommandException;
import exceptions.ModelException;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;
import search.Solution;

public abstract class Model_MVP extends Observable implements IModel 
{
	public Model_MVP() 
	{
		
	}

	@Override
	public void generateMaze3d(String mazeName, String arguments) 
	{
		if (validateName(mazeName) == false) 
		{
			setChanged();
			notifyObservers("EXCEPTION: No name given for maze");
		} 
		else 
		{
			final String[] parameters = arguments.split(" ");

			Future<Maze3d> futureMaze = getThreadPool().submit(new Callable<Maze3d>() 
			{
				Maze3d maze;

				@Override
				public Maze3d call() throws Exception 
				{
					if (parameters.length == 3) 
					{
						int height = Integer.parseInt(parameters[0]);
						int width  = Integer.parseInt(parameters[1]);
						int length = Integer.parseInt(parameters[2]);
						maze = new MyMazeGenerator().generate(height, width, length);
					} 
					else 
					{
						maze = new MyMazeGenerator().generate(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
					}
					return maze;
				}
			});

			try 
			{
				map.put(mazeName, futureMaze.get());
				setChanged();
				notifyObservers("GENERATE: Maze '" + mazeName + "' was Generated.");
			}
			catch (InterruptedException | ExecutionException e) 
			{
				setChanged();
				notifyObservers("EXCEPTION: Generation maze exception occured");
			}
		}
	}

	@Override
	public void displayMaze(String name) throws ModelException 
	{
		Maze3d maze = getMaze(name);
		setChanged();
		notifyObservers(maze);
	}

	@Override
	public void displayCrossSection(String... args) throws CommandException, ModelException 
	{
		String[] splitted = args[0].split(" ");
		if (splitted.length != 4) 
		{
			throw new CommandException("EXCEPTION: Invalid number of arguments");
		}

		String section = splitted[0];
		int index = Integer.parseInt(splitted[1]);
		String mazeName = splitted[3];

		Maze3d maze = getMaze(mazeName);
		int[][] crossSection;
		switch (section.toLowerCase()) 
		{
		case "x":
			crossSection = maze.getCrossSectionByX(index);
			break;
		case "y":
			crossSection = maze.getCrossSectionByY(index);
			break;
		case "z":
			crossSection = maze.getCrossSectionByZ(index);
			break;
		default:
			throw new CommandException("EXCEPTION: Cannot get cross section by " + section);
		}

		setChanged();
		notifyObservers(crossSection);
	}

	@Override
	public void saveMaze(final String mazeName, final String fileName) 
	{
		getThreadPool().execute(new Runnable() 
		{
			@Override
			public void run() 
			{

				try 
				{
					Maze3d maze = getMaze(mazeName);
					FileOutputStream fos = new FileOutputStream(fileName);
					MyCompressorOutputStream compressor = new MyCompressorOutputStream(fos);
					compressor.write(maze.toByteArray());
					compressor.close();
					setChanged();
					notifyObservers("SAVE: Maze '" + mazeName + "' was saved.");
				} 
				
				catch (IOException e) 
				{
					setChanged();
					notifyObservers("EXCEPTION: IO Error with file path.");
				} 
				catch (ModelException e) 
				{
					setChanged();
					notifyObservers("EXCEPTION: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public void loadMaze(final String fileName, final String mazeName) 
	{
		getThreadPool().execute(new Runnable() 
		{
			@Override
			public void run() 
			{
				try {
					MyDecompressorInputStream decompressor = new MyDecompressorInputStream(
							new FileInputStream(fileName));

					byte[] initBytes = new byte[12];
					decompressor.read(initBytes, 0, 12);
					ByteBuffer bufferInit = ByteBuffer.wrap(initBytes);

					int height, width, length;
					height = bufferInit.getInt();
					width = bufferInit.getInt();
					length = bufferInit.getInt();

					int totalSize = height * width * length + 36;

					byte[] totalBytes = new byte[totalSize];
					ByteBuffer bufferTotal = ByteBuffer.wrap(totalBytes);
					bufferTotal.put(initBytes);
					decompressor.read(totalBytes, 12, totalSize - 12);

					Maze3d maze = new Maze3d(totalBytes);
					map.put(mazeName, maze);

					decompressor.close();

					setChanged();
					notifyObservers("LOAD: Maze '" + mazeName + "' was loaded.");
				} 
				catch (IOException e) 
				{
					setChanged();
					notifyObservers("EXCEPTION: IO Error with file name.");
				}
			}
		});
	}

	@Override
	public void mazeSize(String[] args) throws ModelException 
	{
		String mazeName = args[0];
		Maze3d maze = getMaze(mazeName);
		int totalMazeSize;
		totalMazeSize = maze.getHeight() * maze.getLength() * maze.getWidth() + 36;

		setChanged();
		notifyObservers("SIZE: Maze '" + mazeName + "' total memory size: " + totalMazeSize + " bytes.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void solve(String name, String algorithm, String... positions) throws ModelException 
	{
		/*
		 * Open socket, send algorithm name, receive 'alg' from server, send
		 * maze to server, receive Solution from server.
		 */
		Maze3d maze;
		Solution<Position> solution;
		Socket theServer;
		PrintWriter writerToServer = null;
		ObjectOutputStream mazeToServer = null;
		boolean hasSolution = false;
		Position currPosition = null;
		Position tempStartPosition = null;

		maze = getMaze(name);

		if (positions != null && positions.length == 3) 
		{
			int posHeight = Integer.parseInt(positions[1]);
			int posWidth = Integer.parseInt(positions[2]);
			int posLength = Integer.parseInt(positions[0]);
			currPosition = new Position(posHeight, posWidth, posLength);
		}

		if (!maze.getStartPosition().equals(currPosition)) 
		{
			tempStartPosition = maze.getStartPosition();
			maze.setEntryPosition(currPosition);
		}

		if (solutionMap.containsKey(maze))
		{
			hasSolution = true;
		} 
		
		else
		{
			try 
			{
				theServer = new Socket(serverName, port);
				writerToServer = new PrintWriter(theServer.getOutputStream());

				writerToServer.println(algorithm);
				writerToServer.flush();

				BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
				in.readLine();

				mazeToServer = new ObjectOutputStream(theServer.getOutputStream());
				mazeToServer.writeObject(maze);
				mazeToServer.flush();

				ObjectInputStream solutionFromServer = new ObjectInputStream(theServer.getInputStream());
				
				solution = (Solution<Position>) solutionFromServer.readObject();				

				solutionMap.put(maze, solution);

				hasSolution = true;
			} 
			catch (IOException e) 
			{
				setChanged();
				notifyObservers("EXCEPTION: Server Socket error !");
			} 
			
			catch (ClassNotFoundException e2)
			{
				setChanged();
				notifyObservers("EXCEPTION: Server Socket error - ClassNotFoundException");
			}
			
			finally 
			{
				if (writerToServer != null) 
				{
					writerToServer.close();
				}
				
				try 
				{
					if (mazeToServer != null) 
					{
						mazeToServer.close();
					}
				} 
				
				catch (IOException e) 
				{
					
				}
			}
		}

		if (tempStartPosition != null) 
		{
			maze.setEntryPosition(tempStartPosition);
		}

		if (hasSolution)
		{
			setChanged();
			notifyObservers("SOLVE: Maze '" + name + "' was solved.");
		}
	}

	@Override
	public void displaySolution(String[] args) throws ModelException 
	{
		Maze3d maze;
		String[] splitted = args[0].split(" ");
		String mazeName = splitted[0];

		maze = getMaze(mazeName);

		Position currPosition = null;
		Position tempStartPosition = null;

		if (splitted.length == 4) 
		{
			int posHeight = Integer.parseInt(splitted[2]);
			int posWidth = Integer.parseInt(splitted[3]);
			int posLength = Integer.parseInt(splitted[1]);
			currPosition = new Position(posHeight, posWidth, posLength);
		}

		if (!maze.getStartPosition().equals(currPosition)) 
		{
			tempStartPosition = maze.getStartPosition();
			maze.setEntryPosition(currPosition);
		}

		Solution<Position> solution = solutionMap.get(maze);

		if (tempStartPosition != null) 
		{
			maze.setEntryPosition(tempStartPosition);
		}

		if (solution == null)
		{
			throw new ModelException("Solution for maze '" + mazeName + "' not found.");
		}

		setChanged();
		notifyObservers(solution);
	}

	@Override
	public Maze3d getMaze(String mazeName) throws ModelException 
	{
		Maze3d maze;
		if (!validateName(mazeName))
		{
			throw new ModelException("EXCEPTION: No name given for maze");
		}

		maze = map.get(mazeName);
		if (maze == null) 
		{
			throw new ModelException("EXCEPTION: Maze named '" + mazeName + "' not found");
		}

		return maze;
	}

	@Override
	public Solution<Position> getSolution(String mazeName) throws ModelException
	{
		Solution<Position> solution;
		if (!validateName(mazeName))
		{
			throw new ModelException("EXCEPTION: No name given for maze");
		}

		solution = solutionMap.get(mazeName);
		if (solution == null) 
		{
			throw new ModelException("EXCEPTION: Maze named '" + mazeName + "' not found");
		}

		return solution;
	}

	@Override
	public void exit() 
	{

		getThreadPool().execute(new Runnable()
		{
			// Save hashMaps before exiting
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			GZIPOutputStream gos = null;

			@Override
			public void run() 
			{
				try 
				{
					fos = new FileOutputStream(mapsFile, false);
					gos = new GZIPOutputStream(fos);
					oos = new ObjectOutputStream(gos);

					if (map != null) 
					{
						oos.writeObject(map);
					}
					if (solutionMap != null) 
					{
						oos.writeObject(solutionMap);
					}

					oos.flush();
				} 
				
				catch (IOException e)
				{
					setChanged();
					notifyObservers("EXCEPTION: IO Error while trying to save HashMaps.");
				} 
				
				finally 
				{
					try 
					{
						if (oos != null) 
						{
							oos.close();
						}

						if (gos != null) 
						{
							gos.close();
						}

						if (fos != null) 
						{
							fos.close();
						}
					} 
					
					catch (IOException e) 
					{
						setChanged();
						notifyObservers("EXCEPTION: IO Error while trying to close output streams.");
					}
				}
			}
		});

		threadPool.shutdown();
		setChanged();
		notifyObservers("Starting shutdown...");

		try
		{
			threadPool.awaitTermination(60, TimeUnit.SECONDS);
			setChanged();
			notifyObservers("Shutdown complete. Good-bye!");
		}
		catch (InterruptedException e)
		{
			setChanged();
			notifyObservers("EXCEPTION: Awaiting termination interrupted");
		}
	}

	@Override
	public void setAmountThreads(int numThreads) 
	{
		numThreadsPool = numThreads;
	}

	private ExecutorService getThreadPool()
	{
		if (threadPool == null) 
		{
			if (numThreadsPool < 1)
			{
				numThreadsPool = DEFAULT_NUM_THREADS;
			}

			threadPool = Executors.newFixedThreadPool(numThreadsPool);
		}

		return threadPool;
	}

	@Override
	public void getFileSize(final String fileName) 
	{

		getThreadPool().execute(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					File file = new File(fileName);
					long fileSize = file.length();
					setChanged();
					notifyObservers("File size: " + fileSize);
				} 
				catch (NullPointerException e) 
				{
					setChanged();
					notifyObservers("EXCEPTION: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public void loadProperties(final String fileName) 
	{
		getThreadPool().execute(new Runnable() 
		{

			@Override
			public void run() 
			{
				try
				{
					Properties p = new Properties();
					p.load(new FileInputStream(fileName));
					
					String newAlgorithm = p.getProperty("Solving Algorithm");
					String portString = p.getProperty("port");
					
					if(portString!=null) {
						setPort(Integer.parseInt(portString));
					}
					
					String propertyServerName = p.getProperty("ServerName");
					setServerName(propertyServerName);
					
					notifyObservers("SET_ALGORITHM: " + newAlgorithm);
				} 
				
				catch (NullPointerException | FileNotFoundException | GenericException | NumberFormatException e) 
				{
					setChanged();
					notifyObservers("EXCEPTION: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public void getPositions(final String mazeName) throws ModelException 
	{
		if (!validateName(mazeName)) 
		{
			throw new ModelException("EXCEPTION: No name given for maze");
		}

		getThreadPool().execute(new Runnable() 
		{
			Maze3d maze;
			String positions;
			Position startPos, goalPos;

			@Override
			public void run()
			{
				maze = map.get(mazeName);
				startPos = maze.getStartPosition();
				goalPos = maze.getGoalPosition();
				positions = startPos.getHeight() + " " + startPos.getWidth() + " " + startPos.getLength() + " "
						+ goalPos.getHeight() + " " + goalPos.getWidth() + " " + goalPos.getLength();
				setChanged();
				notifyObservers("POSITIONS: " + positions);
			}
		});
	}

	@Override
	public void moveDirection(final String mazeName, final String position, final String direction) throws ModelException 
	{
		if (!validateName(mazeName)) 
		{
			throw new ModelException("EXCEPTION: Invalid maze name");
		}

		if (!map.containsKey(mazeName)) 
		{
			throw new ModelException("EXCEPTION: Maze '" + mazeName + "' doesn't exist.");
		}

		getThreadPool().execute(new Runnable() 
		{
			Maze3d maze;
			String[] positionInts = position.split(" ");
			Position curr = new Position(Integer.parseInt(positionInts[0]), Integer.parseInt(positionInts[1]),
					Integer.parseInt(positionInts[2]));

			@Override
			public void run() 
			{
				boolean moved = true;
				maze = map.get(mazeName);
				switch (direction.toLowerCase()) 
				{
				case "up":
					curr.moveUp();
					break;
				case "down":
					curr.moveDown();
					break;
				case "left":
					curr.moveLeft();
					break;
				case "right":
					curr.moveRight();
					break;
				case "forward":
					curr.moveForwards();
					break;
				case "backward":
					curr.moveBackwards();
					break;
				default:
					moved = false;
					break;
				}

				String notifiedString = null;
				if (moved == false) 
				{
					notifiedString = "EXCEPTION: illegal direction received";
				} 
				else 
				{
					if (maze.isMoveablePosition(curr)) 
					{
						notifiedString = "MOVE: " + curr.getHeight() + " " + curr.getWidth() + " " + curr.getLength();
					}
				}

				if (notifiedString != null) 
				{
					setChanged();
					notifyObservers(notifiedString);
				}
			}
		});
	}

	private boolean validateName(String name) 
	{
		return name != null && !name.isEmpty();
	}

	public void setServerName(String serverName) 
	{
		if (serverName != null) 
		{
			this.serverName = serverName;
		}
	}
	
	public void setPort(int port) 
	{
		if (port > 0) 
		{
			this.port = port;
		}
	}
	
	/********* Members **********/
	
	//constants
	private static final int DEFAULT_SIZE = 5;

	private static final int DEFAULT_NUM_THREADS = 5;

	HashMap<String, Maze3d> map;

	HashMap<Maze3d, Solution<Position>> solutionMap;

	ExecutorService threadPool;

	String serverName = "localhost";

	int port = 5400;

	File mapsFile;

	int numThreadsPool;
}
