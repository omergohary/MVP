/**
 * @file BasicWindow.java
 * 
 * @author Colman & Omer Gohary
 * 
 * @description This file represents a basic abstact window
 * 				
 * @date    28/10/2015
 */
package GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow implements Runnable 
{
	public BasicWindow(String title, int width, int height) 
	{
		m_widgetsInitialized = false;
		m_display 			 = new Display();
		m_shell				 = new Shell(m_display);
		m_shell.setSize(width, height);
		m_shell.setText(title);
	}

	abstract void initWidgets();
	
	/**
	 * Lets you initialize the widgets before opening the window.
	 * Can be used to set listeners after initializing.
	 * Will initialize the widgets only once, if even run multiple times.
	 */
	public void actualInitWidgets() 
	{
		if (!m_widgetsInitialized) 
		{
			initWidgets();
			m_widgetsInitialized = true;
		}
	}

	@Override
	public void run() 
	{
		actualInitWidgets();
		m_shell.open();
		
		// main event loop, while window isn't closed
		while (!m_shell.isDisposed()) 
		{ 
			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!m_display.readAndDispatch()) 
			{ 					   // if the queue is empty
				m_display.sleep(); // sleep until an event occurs
			}
		} // shell is disposed

		m_display.dispose(); // dispose OS components
	}
	
	/******************* MEMBERS ****************/
	
	Display m_display;
	Shell m_shell;
	boolean m_widgetsInitialized;
}
