/**
 * @file IMazeDisplayable.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is an implementation of the IDisplayable interface to the maze3D display
 * 				
 * @date    24/09/2015
 */

package view;

public interface IMazeDisplayable extends IDisplayable
{

	int[][] getMazeCrossSection();
}
