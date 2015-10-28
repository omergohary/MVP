package boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import GUI.MazeWindow;
import GUI.MyView_GUI;
import presenter.MazeProperties;
import presenter.MyPresenter;
import model.MyModel;
import view.MyView;
import view.View;


public class Run 
{

	public static void main(String[] args) throws IOException
	{
		// Get configuration parameters
		MazeProperties properties = MazeProperties.getInstance();
		properties.loadConfigFile();
		
		// check which view the user asked for
		switch (properties.getCLIorGUI())
		{
			case("CLI"):
			{
				main_CLI();
				break;
			}
			
			case("GUI"):
			{
				main_GUI();
				break;
			}
			
			default:
			{
				System.out.println("Bad type of view - please change to CLI or GUI only");
			}
		}
		

	}
	
	// Run project with CLI
	private static void main_CLI() throws IOException 
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
		
		/********** View *********/
		BufferedReader inFile = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter outFile   = new PrintWriter(System.out, true);
		MyView view 		  = new MyView(inFile, outFile);
	
		/********** Model *********/
		MyModel model 	 = new MyModel();
		
		/******* Presenter ********/
		MyPresenter presenter = new MyPresenter(view, model);
		
		// add the presenter to the model notifications' services
		view.addObserver(presenter);
		model.addObserver(presenter);
				
		view.start();
	}	private static void main_GUI() 
	{		
		/********** View *********/
		MyView_GUI view = new MyView_GUI();
		
		/********** Model *********/
		MyModel model 	 = new MyModel();
		
		/******* Presenter ********/
		MyPresenter presenter = new MyPresenter(view, model);
		
		// add the presenter to the model notifications' services
		view.addObserver(presenter);
		model.addObserver(presenter);
		
		view.start();
		
	}
	
	// Make new config file
	public static void _main(String[] args) throws IOException
	{
		MazeProperties.getInstance().setConfigToFile(20, "BFS", "SimpleGeneration", "GUI");
		System.out.println("Writing new XML succeeded");
	}
	
}

