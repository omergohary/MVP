/**
 * @file CLI.java
 * 
 * @author Omer Gohary
 * 
 * @description This file is responsible on the command line interface of the project
 * 				
 * @date    24/09/2015
 */

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.commands.ICommand;

/**
 * This class represents the command line interface of our system
 */
public class CLI 
{
	/**
	 * Instantiates a new cli.
	 *
	 * @param input the input
	 * @param output the output
	 * @param aMap the a map
	 */
	public CLI(InputStream input, OutputStream output, HashMap<String, ICommand> aMap) 
	{
		this.in = new BufferedReader(new InputStreamReader(input));
		this.out = new PrintWriter(output);
		this.map = aMap;
	}

	/**
	 * This function is running in a parallel thread and is responsible 
	 * on getting commands from the user and handle them.
	 * 
	 * @throws IOException
	 */
	public void start() 
	{
		
		out.println("Trying to open CLI thread...");
		out.flush();
		mainThread = new Thread(new Runnable() 
		{
			String line;
			ICommand command;

			@Override
			public void run() 
			{
				out.println("Opened new CLI thread!");
				out.flush();
				try {
					do {
						out.println("\nEnter a command:");
						out.flush();
						
						line = in.readLine();

						String joined;
						String[] splitted = line.split("\\s+");
						int lastWordIndex = splitted.length + 1;
						List<String> joinStrings;
						List<String> joingArgStrings;

						do {
							joinStrings = new ArrayList<>(--lastWordIndex);
							
							for (int i = 0; i < lastWordIndex; i++) 
							{
								joinStrings.add(splitted[i]);
							}

							joined = join(joinStrings ," ");
							command = map.get(joined);

						} while (command == null && lastWordIndex > 0);

						if (command != null) 
						{
							// build parameters string
							joingArgStrings = new ArrayList<>(splitted.length - lastWordIndex);
							
							for (int i = lastWordIndex; i < splitted.length; i++) 
							{
								joingArgStrings.add(splitted[i]);
							}
							
							String arguments = join(joingArgStrings, " ");

							if (joined.equals("exit")) 
							{
								stayInLoop = false;
							}

							command.doCommand(arguments);
						} else {
							out.println("Command '" + line + "' is invalid!");
							out.flush();
						}

					} while (stayInLoop);
					out.println("Exitting CLI thread...");
					out.flush();

				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		});

		mainThread.start();
	}
	
	public static String join(List<String> strings, String separator)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < strings.size(); i++)
		{
			sb.append(strings.get(i));
			if(i < strings.size() - 1)
				sb.append(separator);
		}
		return sb.toString();				
	}
	
	
	/************ Members ***********/
	
	/** The in. */
	BufferedReader in;
	
	/** The out. */
	PrintWriter out;
	
	/** The map. */
	HashMap<String, ICommand> map;
	
	/** The main thread. */
	Thread mainThread;
	
	/** The stay in loop. */
	volatile boolean stayInLoop = true;

}
