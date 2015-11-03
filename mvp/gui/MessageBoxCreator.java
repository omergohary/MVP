/**
 * @file MessageBoxCreator.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a class that responsible to create different types of message-boxs
 * 				
 * @date    28/10/2015
 */

package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxCreator 
{	
	public static int createErrorMessageBox(Shell parent, String errorMessage) 
	{
		return createMessageBox(parent, SWT.ERROR | SWT.OK, "Error occured!", errorMessage);
	}
	
	public static int createNotificationMessageBox(Shell parent, String notificationMessage) 
	{
		return createNotificationMessageBox(parent, "Notification", notificationMessage);
	}
	
	public static int createNotificationMessageBox(Shell parent, String title, String notificationMessage)
	{
		return createMessageBox(parent, SWT.ICON_INFORMATION | SWT.OK, title, notificationMessage);
	}
	
	public static int createMessageBox(final Shell parent, final int style, final String title, final String message) 
	{
		parent.getDisplay().syncExec(new Runnable() 
		{
			@Override
			public void run() 
			{
				MessageBox mb = new MessageBox(parent, style);
				mb.setText(title);
				mb.setMessage(message);
				m_retVal = mb.open();
			}
		});
		
		return m_retVal;
	}
	
	/*********** Members ***********/
	
	static int m_retVal;
}
