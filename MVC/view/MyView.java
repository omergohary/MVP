/**
 * @file MyView.java
 * 
 * @author Omer Gohary
 * 
 * @description This class is the concrete view class in MVC design
 * 				
 * @date    24/09/2015
 */


package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * The Class MyView.
 */
public class MyView extends CommonView 
{
	
	/** The reader. */
	BufferedReader reader;
	
	/** The writer. */
	PrintWriter writer;
	
	/**
	 * Instantiates a new my view.
	 */
	public MyView() 
	{
		in = System.in;
		reader = new BufferedReader(new InputStreamReader(in));
		out = System.out;
		writer = new PrintWriter(out);
	}


	@Override
	public void display(IDisplayable displayable) 
	{
		displayable.display(out);
	}
}
