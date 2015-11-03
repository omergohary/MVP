/**
 * @file ServerRunner.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the run function of the server side
 * 				
 * @date    17/10/2015
 */

package serverSide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import exceptions.GenericException;
import presenter.Properties;

public class ServerRunner 
{

	public ServerRunner() 
	{
		init();
	}

	@SuppressWarnings("unused")
	private void init() 
	{
		serverPropertyFile = new File(PROPERTY_SERVER_STRING);
		properties = new Properties();
		try 
		{
			properties.load(new FileInputStream(serverPropertyFile));
			port = Integer.parseInt(properties.getProperty("Port"));
			numClients = Integer.parseInt(properties.getProperty("NumClients"));
		} 
		
		catch (FileNotFoundException | GenericException |NumberFormatException e) 
		{
			port = DEFAULT_PORT;
			numClients = DEFAULT_NUM_CLIENTS;
			properties.addProperty("Port", port + "");
			properties.addProperty("NumClients", numClients + "");
			try 
			{
				properties.save(new FileOutputStream(serverPropertyFile));
			} 
			catch (FileNotFoundException e1) 
			{
				
			}
		}

		model = new ModelServer(port, new MyClientHandler(), numClients);
		view = new ViewServer();

		ControllerServer controller = new ControllerServer(model, view);
	}

	public void start() 
	{
		view.start();
	}

	public static void main(String[] args) 
	{
		ServerRunner sr = new ServerRunner();
		sr.start();
	}
	
	/************ CONSTS **************/
	private static final int DEFAULT_PORT = 5400;
	private static final int DEFAULT_NUM_CLIENTS = 6;
	private static final String PROPERTY_SERVER_STRING = "serverProperty.xml";

	/*********** MEMBERS *************/
	
	ModelServer model;
	ViewServer view;
	Properties properties;
	File serverPropertyFile;

	int port;
	int numClients;

}
