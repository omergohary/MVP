/**
 * @file MyObservableCLI.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file the observable CLI-view  
 * 				
 * @date    2/10/2015
 */

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyObservableCLI extends view_MVP 
{
	
	public MyObservableCLI() 
	{
		in = System.in;
		reader = new BufferedReader(new InputStreamReader(in));
		out = System.out;
		writer = new PrintWriter(out);
	}
	
	public MyObservableCLI(InputStream aInput, OutputStream aOutput) 
	{
		in = aInput;
		reader = new BufferedReader(new InputStreamReader(in));
		out = aOutput;
		writer = new PrintWriter(out);
	}
	
	@Override
	public void start() 
	{
		// new thread
		new Thread(new Runnable() 
		{
			@Override
			public void run() {
				String line = null;
				MyDisplayable displayable = new MyDisplayable();
				
				displayable.setMessage("Please input commands.");
				display(displayable);

				do 
				{
					try 
					{
						line = reader.readLine();
						setChanged();
						notifyObservers(line);
					} 
					catch (IOException e) 
					{
						displayable.setMessage(e.getMessage());
						display(displayable);
					}
					
				} while (!(line.toLowerCase().equals("exit")));

				displayable.setMessage("Stopping user input.");
				display(displayable);
			}
		}).start();
	}

	@Override
	public void display(IDisplayable displayable) 
	{
		if (displayable != null)
		{
			displayable.display(out);
		}
	}
	
	/**************** Members **************/
	
	InputStream in;
	OutputStream out;
	BufferedReader reader;
	PrintWriter writer;
}
