/**
 * @file AStarSearcherTest.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents the g-test to A-Star class
 * 				
 * @date    28/10/2015
 */
package search;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import demo.*;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;

public class AStarSearcherTest 
{
	@Test
	public void test_setHeuristicFunction() 
	{
		AstarSearcher<Position> testedClass = new AstarSearcher<>(new MazeAirHeuristic());
		
		// invalid heuristic
		assertFalse(testedClass.setHeuristic(null));
		
		// valid heuristic
		assertTrue(testedClass.setHeuristic(new MazeManhattanHeuristic()));
	}
	
	@Test
	public void test_CTor() 
	{
		AstarSearcher<Position> testedClass = new AstarSearcher<>(null);
		
		Throwable execptionReceived = null;

		 try 
		 {
			 // do valid action
			Maze3d generatedMaze = new SimpleMaze3dGenerator().generate(6, 6, 6);
			Maze3dSearchable maze3dAdapter = new Maze3dSearchable(generatedMaze);
			
		    testedClass.search(maze3dAdapter);
		 } 
		 
		 catch (Throwable ex) 
		 {
			 execptionReceived = ex;
		 }

		  assertTrue(execptionReceived instanceof NullPointerException);
	}
	
	@Test
	public void test_searchFunction() 
	{
		AstarSearcher<Position> testedClass = new AstarSearcher<>(new MazeAirHeuristic());
		
		Throwable execptionReceived = null;

		 try 
		 {
		    testedClass.search(null);
		 } 
		 
		 catch (Throwable ex) 
		 {
			 execptionReceived = ex;
		 }

		  assertTrue(execptionReceived instanceof NullPointerException);
	}
	
	@Test
	public void test_invalidMazeToSearch() 
	{
		AstarSearcher<Position> testedClass = new AstarSearcher<>(new MazeAirHeuristic());
		
		Throwable execptionReceived = null;

		 try 
		 {
			// do valid action with invalid index-X
			Maze3d generatedMaze = new SimpleMaze3dGenerator().generate(-1, 9, 14);
			Maze3dSearchable maze3dAdapter = new Maze3dSearchable(generatedMaze);
			
		    testedClass.search(maze3dAdapter);
		 } 
		 
		 catch (Throwable ex) 
		 {
			 execptionReceived = ex;
		 }

		 // the maze3d Generation return null, therefore it should pass
		  assertTrue(execptionReceived instanceof NullPointerException);
	}
	
	@Test
	public void test_validMazeToSearch() 
	{
		AstarSearcher<Position> testedClass = new AstarSearcher<>(new MazeAirHeuristic());
		
		Throwable execptionReceived = null;

		 try 
		 {
			// do valid action
			Maze3d generatedMaze2 = new SimpleMaze3dGenerator().generate(2, 9, 14);
			Maze3dSearchable maze3dAdapter2 = new Maze3dSearchable(generatedMaze2);
			
		    testedClass.search(maze3dAdapter2);
		 } 
		 
		 catch (Throwable ex) 
		 {
			 execptionReceived = ex;
		 }

		  assertFalse(execptionReceived instanceof NullPointerException);
	}

	  
	@Test
	public void test_addToOpenList() 
	{
		AstarSearcher<Position> testedClass = new AstarSearcher<>(new MazeAirHeuristic());
		
		Throwable execptionReceived = null;

		 try 
		 {
		    testedClass.addToOpenList(null);
		 } 
		 
		 catch (Throwable ex) 
		 {
			 execptionReceived = ex;
		 }

		 // the maze3d Generation return null, therefore it should pass
		 assertTrue(execptionReceived instanceof NullPointerException);
		  
	}
}


