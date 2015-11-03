/**
 * @file view_MVP.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the view in mvp design
 * 				
 * @date    2/10/2015
 */

package view;

import java.util.HashSet;
import java.util.Observable;

public abstract class view_MVP extends Observable implements IView 
{
	public view_MVP() 
	{
		legalAlgorithms = new HashSet<>();
		legalAlgorithms.add("air");
		legalAlgorithms.add("manhatten");
		legalAlgorithms.add("bfs");
		
		currAlgorithm = DEFAULT_SOLVING_ALGORITHM;
	}

	public void setAlgorithm(String algorithm) 
	{
		if(legalAlgorithms.contains(algorithm)) 
		{
			currAlgorithm = algorithm;
		}
	}
	
	/********** Members and consts **********/
	
	private static final String DEFAULT_SOLVING_ALGORITHM = "air";
	protected String currAlgorithm;
	HashSet<String> legalAlgorithms;
}
