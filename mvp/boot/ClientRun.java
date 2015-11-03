/**
 * @File ClientRun.java
 * 
 * @details This file is responsible on running a single 3Dmaze client
 * 
 * @author Omer Gohary
 * 
 * @author 20/10/2015
 */

package boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import exceptions.GenericException;
import gui.MyObservableGUI;
import model.Model_MVP;
import model.MyObservableModel;
import presenter.MVP_presenter;
import presenter.Properties;
import view.view_MVP;
import view.MyObservableCLI;

/**
 * This class is the main client's run function
 */
public class ClientRun 
{
	public static void main(String[] args) 
	{
		SetUp();
		m_view.start();
	}
	
	private static void SetUp()
	{
		m_propertyFile = ConfigFileName;
		m_file = new File(m_propertyFile);
		
		// if the xml is not exist, create one with default params
		if (m_file.exists() == false) 
		{
			initProperties();
		}
		
		init();
	}
	
	private static void initProperties() 
	{
		Properties prop = new Properties();
		
		prop.addProperty("Solving Algorithm", "manhatten");
		prop.addProperty("NumThreads", "10");
		prop.addProperty("ViewToUse", "GUI");
		prop.addProperty("port", "5400");
		prop.addProperty("ServerName", "localhost");
		
		try 
		{
			prop.save(new FileOutputStream(m_propertyFile));
			System.out.println("Writing new XML succeeded");
		} 
		
		catch (FileNotFoundException e) 
		{
			
		}
	}
	
	private static void init() 
	{
		m_properties = new Properties();
		
		try 
		{
			m_properties.load(new FileInputStream(m_propertyFile));
			m_solvingAlgorithm 	  = m_properties.getProperty("Solving Algorithm");
			m_generationAlgorithm = m_properties.getProperty("Generation Algorithm");
			m_numThreads 		  = Integer.parseInt(m_properties.getProperty("NumThreads"));
			m_viewToUse 	      = m_properties.getProperty("View");
		} 
		
		catch (FileNotFoundException | GenericException | NumberFormatException exception) 
		{
			// default values:
			m_generationAlgorithm = "MyGeneration3dMaze";
			m_viewToUse		      = "GUI";
		}
		
		if (m_viewToUse != null && m_viewToUse.toLowerCase().equals("cli")) 
		{
			CLI_Print();
			m_view = new MyObservableCLI();
		} 
		
		else  // "GUI"
		{
			m_view = new MyObservableGUI();
		}
		
		m_model 	= new MyObservableModel();
		m_presenter = new MVP_presenter(m_model, m_view);
		m_model.setAmountThreads(m_numThreads);
		m_view.setAlgorithm(m_solvingAlgorithm);
	}
	
	private static void CLI_Print()
	{
		System.out.println("****************************************************************");
		System.out.println(" (1) dir														");
		System.out.println(" 			ex. dir ./bin										");
		System.out.println(" (2) generate3dMaze <maze name> <dimX> <dimY> <dimZ>			");
		System.out.println(" 			ex. generate3dMaze OmerMaze 3 3 3					");
		System.out.println(" (3) display3dMaze <maze name>                      		    ");
		System.out.println(" 			ex. display3dMaze OmerMaze                    	    ");
		System.out.println(" (4) displayCrossSection <maze name> <dim> <index>  		    ");
		System.out.println(" 			ex. displayCrossSection OmerMaze Y 2           	    ");
		System.out.println(" (5) saveMaze <maze name> <outfile name>           	        	");
		System.out.println(" 			ex. saveMaze OmerMaze omerMaze.txt             	    ");
		System.out.println(" (6) loadMaze <infile name> <new maze name>         		    ");
		System.out.println(" 			ex. loadMaze omerMaze.txt omerMaze2            	    ");
		System.out.println(" (7) mazeSize <maze name>            						    ");
		System.out.println(" 			ex. mazeSize OmerMaze             			 	 	");
		System.out.println(" (8) fileSize <maze name>            				 		    ");
		System.out.println(" 			ex. fileSize OmerMaze             				 	");
		System.out.println(" (9) solve <maze name> <algirthm> (BFS/AirDistance/Manhattan)   ");
		System.out.println(" 			ex. solve OmerMaze BFS            				    ");
		System.out.println(" (10) displaySolution <maze name>                               ");
		System.out.println(" 			ex. displaySolution OmerMaze               			");
		System.out.println(" (11) saveSolutionMap <outFile name>                            ");
		System.out.println(" 			ex. saveSolutionMap SolutionsMap.zip           		");
		System.out.println(" (12) loadSolutionMap <inFile name>                             ");
		System.out.println(" 			ex. loadSolutionMap SolutionsMap.zip           		");
		System.out.println(" (13) exit													    ");
		System.out.println("****************************************************************");
	}

	
	/***************** Members ****************/
	
	private static Properties m_properties;
	private static String m_propertyFile;
	private static String m_solvingAlgorithm;
	private static String m_generationAlgorithm;
	private static String m_viewToUse;
	private static int m_numThreads;
	private static File m_file;
	
	/** MVP instances **/
	
	private static Model_MVP     m_model;
	private static view_MVP      m_view;
	private static MVP_presenter m_presenter;
	
	/** Consts **/
    private static final String ConfigFileName = "properties.xml";
	
}
