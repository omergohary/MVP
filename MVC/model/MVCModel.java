/**
 * @file MVCModel.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents the model interface in MVC design
 * 				
 * @date    24/09/2015
 */

package model;

import controller.IController;

public interface MVCModel extends IModel 
{

	/**
	 * Sets the controller.
	 *
	 * @param aController the new controller
	 */
	public void setController(IController aController);

}
