/**
 * @file MyController.java
 * 
 * @author Omer Gohary
 * 
 * @description This file implements the controller interface
 * 				
 * @date    24/09/2015
 */


package controller;

import model.MVCModel;
import view.MVCView;

/**
 * This class implements the controller in the MVC architectural pattern
 */
public class MyController extends CommonController 
{
	/**
	 * Instantiates a new my controller.
	 *
	 * @param aModel the a model
	 * @param aView the a view
	 */
	public MyController(MVCModel aModel, MVCView aView) 
	{
		super(aModel, aView);
	}
}
