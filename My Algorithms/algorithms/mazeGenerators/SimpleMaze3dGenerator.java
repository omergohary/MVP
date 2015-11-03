/**
 * @file SimpleMaze3dGenerator.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a generator of simple random 3d maze
 * 
 * @date    12/08/2015
 */

package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMaze3dGenerator extends CommonMaze3dGenerator 
{
	Random rnd = new Random();
	
	/**
	 * C-tor
	 */
	public SimpleMaze3dGenerator() 
	{
		
	}
	
	@Override
	public Maze3d generate(int height, int width, int length)
	{
		Maze3d maze = new Maze3d(height, width, length);
		Position entryPoint = generateEdgePosition(maze);
		Position exitPoint = generateEdgePosition(maze);
		System.out.println("Entry point: " + entryPoint.getHeight() + ", " +entryPoint.getWidth() + ", " +entryPoint.getLength());
		System.out.println("Exit point: " + exitPoint.getHeight() + ", " +exitPoint.getWidth() + ", " +exitPoint.getLength());
		randomizeWalls(maze);
		//System.out.println("Before making path:");
		//maze.printMaze();
		makePath(maze, entryPoint, exitPoint);
		return maze;
	}
	/**
	 * This function is responsible to do set random walls in the maze
	 * @param maze - the maze to set there
	 */
	protected void randomizeWalls(Maze3d maze)
	{
		int maxHeight = maze.getHeight();
		int maxWidth  = maze.getWidth();
		int maxLength = maze.getLength();
		
		int maxWallsNum = maxHeight * maxWidth * maxLength;
		int wallsToGenerate = rnd.nextInt(maxWallsNum);
		
		for(int i=0; i<wallsToGenerate;i++)
		{
			maze.setWall(new Position(rnd.nextInt(maxHeight), rnd.nextInt(maxWidth), rnd.nextInt(maxLength)));
		}
	}
	
	/**
	 * This function is responsible to make a path in the maze by:
	 * @param maze    - the instance of the 3d maze
	 * @param current - the current position
	 * @param exit    - the goal position
	 */
	private void makePath(Maze3d maze, Position current, Position exit)
	{
		Position tmp;
		while (!current.equals(exit))
		{
			tmp = current.clone();
			
			// Makes sure the move we make is legal
			do {
				current = tmp.clone();
				current.moveInRandom();
			} while(!maze.isLegalPosition(current));
			
			maze.setPass(current);
		}
	}
}
