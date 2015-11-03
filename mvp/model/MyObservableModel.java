/**
 * @file MyObservableModel.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the observable abstract model in MVP design
 * 				
 * @date    02/11/2015
 */

package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import search.Solution;

public class MyObservableModel extends Model_MVP 
{
	public MyObservableModel() 
	{
		mapsFile = new File("maps.bin");
		initModel();
	}
	
	@SuppressWarnings("unchecked")
	private void initModel() {
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		ObjectInputStream ois = null;

		try 
		{
			fis = new FileInputStream(mapsFile);
			gis = new GZIPInputStream(fis);
			ois = new ObjectInputStream(gis);
			map = (HashMap<String, Maze3d>) ois.readObject();
			solutionMap = (HashMap<Maze3d, Solution<Position>>) ois.readObject();
		} 
		catch (IOException | ClassNotFoundException e) 
		{
			map = new HashMap<>();
			solutionMap = new HashMap<>();
		} 
		finally 
		{
			try 
			{
				if (ois != null) 
				{
					ois.close();
				}
				if (fis != null) 
				{
					fis.close();
				}
			} 
			
			catch (IOException e) 
			{

			}
		}
	}
}
