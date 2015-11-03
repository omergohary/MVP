/**
 * @file Run.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a main runner of the MVC project
 * 				
 * @date    24/09/2015
 */

package boot;

import controller.MyController;
import model.MyModel;
import view.MyView;

/** Run main logic **/
public class Run 
{
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		/********** View **********/
		MyView view = new MyView();

		/********** Model **********/
		MyModel model = new MyModel();
		
		/********* Control *********/
		MyController controller = new MyController(model, view);
		
		// run
		view.start();

	}

}
