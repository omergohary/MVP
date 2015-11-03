/**
 * @file ControllerServer.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the controller in server (MVC), gives an access of this class to the model and view
 * 				
 * @date    10/10/2015
 */

package serverSide;

public class ControllerServer 
{

	public ControllerServer(ModelServer m, ViewServer v) 
	{
		model = m;
		view = v;
		
		model.setController(this);
		view.setController(this);
	}
	
	public void display(String text) 
	{
		view.display(text);
	}

	public void startModel() 
	{
		try 
		{
			model.start();
		} 
		catch (Exception e) 
		{
			view.display(e.getMessage());
		}
	}

	public void stopModelServer() 
	{
		try 
		{
			model.stop();
		} 
		
		catch (Exception e)
		{
			view.display(e.getMessage());
		}
	}
	
	/***** Members *****/
	
	ModelServer model;
	ViewServer view;
}
