/**
 * @file BasicDialog.java
 * 
 * @author Colman & Omer Gohary
 * 
 * @description This file represents a class that responsible to generate a basic dialog
 * 				
 * @date    28/10/2015
 */

package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class represents a basic dialog in the GUI
 */
public abstract class BasicDialog extends Dialog 
{	
	
	public BasicDialog(Shell parent) 
	{
		super(parent);
	}

	public BasicDialog(Shell parent, int style) 
	{
		super(parent, style);
	}
	
	abstract protected void initWidgets();
	
	public int open() 
	{
		initWidgets();
		m_shell.pack();
		m_shell.open();
		m_shell.layout();

		Display display = getParent().getDisplay();
		
		while (!m_shell.isDisposed())
		{
			if (!display.readAndDispatch()) 
			{
				display.sleep();
			}
		}

		return m_retVal;
	}
	
	/*********** Members ************/
	
	Shell m_shell;
	int m_retVal;
}
