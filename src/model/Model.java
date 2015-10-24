/**
 * @file Model.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface to the model in the MVP
 * 				
 * @date    24/09/2015
 */

package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Presenter;

public interface Model
{
	/**
	 * Setter to the presenter data member (MVP)
	 * 
	 * @param presenterToCommunicate - the presenter to set
	 */
	public void setPresenter(Presenter presenterToCommunicate);
	
	/**
	 * This function is responsible on generating 3d maze
	 * @param mazeName     - a unique name
	 * @param dimX         - dimension X
	 * @param dimY		   - dimension Y
	 * @param dimZ		   - dimension Z
	 * @throws IOException
	 */
	public void generate3dMaze(String mazeName, int dimX, int dimY, int dimZ) throws IOException;
	
	/**
	 * This function is responsible to return a 3d maze according to its name
	 * 
	 * @param mazeName - the maze to return
	 * 
	 * @return the maze3d instance
	 */
	public Maze3d getSaved3dMaze(String mazeName);
	
	/**
	 * This function is responsible to compress an exist maze to specific outfile
	 * @param mazeName - the maze to compress
	 * @param fileName - the out file after compress
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void compressMaze(String mazeName, String fileName) throws FileNotFoundException, IOException;
	
	/**
	 * This function is responsible to de-compress a maze from input file, and save it
	 * @param fileToRead  - the input file that the compressed maze is here
	 * @param mazeNewName - the new name to call the maze
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void decompressMaze(String fileToRead, String mazeNewName) throws FileNotFoundException, IOException;
	
	/**
	 * This function is responsible to calculate the maze's size and print it
	 * 
	 * @param mazeToCheck - maze's name
	 */
	public void printMazeSize(String mazeToCheck);
	
	/**
	 * This function is responsible to calculate the maze's size *in file* and print it
	 * 
	 * @param mazeToCheck - maze's name
	 */
	public void printFileSize(String mazeToCheck);
	
	/**
	 * This function is responsible to solve specific maze with specific algorithm
	 * @param mazeName  - the required maze
	 * @param algorithm - the algorithm
	 */
	public void solveMaze(String mazeName, String algorithm);
	
	/**
	 * This function is responsible to get the solution of specific maze
	 * @param mazeName - maze's name
	 * @return - the solution instance
	 */
	public Solution<Position> getSolution(String mazeName);
}
