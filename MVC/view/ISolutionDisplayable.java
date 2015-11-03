/**
 * @file ISolutionDisplayable.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is an implementation of the IDisplayable interface to the solution of 3d maze display
 * 				
 * @date    24/09/2015
 */


package view;

import search.Solution;

public interface ISolutionDisplayable<T> extends IDisplayable 
{

	public Solution<T> getSolution();
}
