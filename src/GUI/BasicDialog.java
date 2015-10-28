/**
 * @file BasicDialog.java
 * 
 * @author Colman
 * 
 * @description This file represents a class that responsible to generate a basic dialog
 * 				
 * @date    28/10/2015
 */
package GUI;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

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
	
	/************* Members ************/
	
	protected Shell m_shell;
	protected int m_retVal;
}
