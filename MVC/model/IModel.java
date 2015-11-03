/**
 * @file IModel.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface to the model in the MVC
 * 				
 * @date    24/09/2015
 */

package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import exceptions.GenerateException;
import exceptions.MVCException;
import exceptions.ModelException;
import search.Solution;

/**
 * The Interface IModel.
 */
public interface IModel {
	
	/**
	 * Generate maze3d.
	 *
	 * @param mazeName the maze name
	 * @param arguments the arguments
	 * @throws GenerateException the generate exception
	 */
	public void generateMaze3d(String mazeName, String arguments) throws ModelException;

	/**
	 * Display maze.
	 *
	 * @param name the name
	 * @throws ModelException the model exception
	 */
	public void displayMaze(String name) throws ModelException;

	/**
	 * Display cross section.
	 *
	 * @param args the args
	 * @throws MVCException the MVC exception
	 */
	public void displayCrossSection(String... args) throws MVCException;

	/**
	 * Save maze.
	 *
	 * @param mazeName the maze name
	 * @param fileName the file name
	 * @throws ModelException the model exception
	 */
	public void saveMaze(String mazeName, String fileName) throws ModelException;

	/**
	 * Load maze.
	 *
	 * @param fileName the file name
	 * @param mazeName the maze name
	 * @throws ModelException the model exception
	 */
	public void loadMaze(String fileName, String mazeName) throws ModelException;

	/**
	 * Maze size.
	 *
	 * @param args the args
	 * @throws ModelException the model exception
	 */
	public void mazeSize(String[] args) throws ModelException;

	/**
	 * Solve.
	 *
	 * @param name the name
	 * @param algorithm the algorithm
	 * @throws ModelException the model exception
	 */
	public void solve(String name, String algorithm, String... position) throws ModelException;

	/**
	 * Display solution.
	 *
	 * @param args the args
	 * @throws ModelException the model exception
	 */
	
	public void loadProperties(String fileName);
	
	public void displaySolution(String[] args) throws ModelException;
	
	public Maze3d getMaze(String mazeName) throws ModelException;
	
	public Solution<Position> getSolution(String mazeName) throws ModelException;

	public void exit();
	
	public void setAmountThreads(int numThreads);

	public void getFileSize(String fileName) throws ModelException;

	public void getPositions(String mazeName) throws ModelException;

	public void moveDirection(String mazeName, String position, String direction) throws ModelException;

}
