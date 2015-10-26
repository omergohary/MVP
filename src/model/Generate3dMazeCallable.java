/**
 * @file Generate3dMazeCallable.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a callable that return 3d maze (for thread pool)
 * 				
 * @date    24/09/2015
 */

package model;

import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;

public class Generate3dMazeCallable implements Callable<Maze3d> 
{
	Generate3dMazeCallable(int x, int y, int z)
	{
		m_dimX = x;
		m_dimY = y;
		m_dimZ = z;
	}
	
	@Override
	public Maze3d call() throws Exception 
	{
		return new SimpleMaze3dGenerator().generate(m_dimX, m_dimY, m_dimZ);
	}
	
	/************ MEMBERS *************/
	
	int m_dimX;
	int m_dimY;
	int m_dimZ;

}
