/**
 * @file GenerateMazeDialog.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a class that responsible to generate a maze's dialog
 * 				
 * @date    28/10/2015
 */
package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GenerateMazeDialog extends BasicDialog 
{
	public GenerateMazeDialog(Shell parent) 
	{
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public GenerateMazeDialog(Shell parent, int style) 
	{
		super(parent, style);
		m_retVal = SWT.CANCEL; // default
	}

	@Override
	protected void initWidgets() 
	{
		m_shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		m_shell.setLayout(new GridLayout(12, true));
		m_shell.setText("Generate 3D Maze");

		m_nameLabel = new Label(m_shell, SWT.NULL);
		m_nameLabel.setText("Maze Name:");
		m_nameLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		m_nameText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		m_nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		m_heightLabel = new Label(m_shell, SWT.NULL);
		m_heightLabel.setText("Maze Height (Y):");
		m_heightLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		m_heightText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		m_heightText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		m_widthLabel = new Label(m_shell, SWT.NULL);
		m_widthLabel.setText("Maze Width (Z):");
		m_widthLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		m_widthText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		m_widthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		m_lengthLabel = new Label(m_shell, SWT.NULL);
		m_lengthLabel.setText("Maze Length (X):");
		m_lengthLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		m_lengthText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		m_lengthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		m_spacer = new Label(m_shell, SWT.NONE);
		m_spacer.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));

		m_okButton = new Button(m_shell, SWT.PUSH);
		m_okButton.setText("OK");
		m_okButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));
		
		m_okButton.addSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (trySetMembers()) 
				{
					m_retVal = SWT.OK;
					m_shell.close();
				} 
				else 
				{
					MessageBoxCreator.createErrorMessageBox(m_shell, "Illegal values entered.");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_cancelButton = new Button(m_shell, SWT.PUSH);
		m_cancelButton.setText("Cancel");
		m_cancelButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));
		m_cancelButton.addSelectionListener(new SelectionListener() 
		{

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_retVal = SWT.CANCEL;
				m_shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});
	}

	public String getMazeName() 
	{
		return m_mazeName;
	}

	public void setMazeName(String aMazeName) 
	{
		this.m_mazeName = aMazeName;
	}

	public int getHeight()
	{
		return m_mazeHeight;
	}

	public void setHeight(int height) 
	{
		this.m_mazeHeight = height;
	}

	public int getWidth()
	{
		return m_mazeWidth;
	}

	public void setWidth(int width)
	{
		this.m_mazeWidth = width;
	}

	public int getLength()
	{
		return m_mazeLength;
	}

	public void setLength(int length)
	{
		this.m_mazeLength = length;
	}

	private boolean trySetMembers()
	{
		try 
		{
			this.m_mazeHeight = Integer.parseInt(m_heightText.getText());
			this.m_mazeLength = Integer.parseInt(m_lengthText.getText());
			this.m_mazeWidth = Integer.parseInt(m_widthText.getText());
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
		
		m_mazeName = m_nameText.getText();
		return (m_mazeName!=null && m_mazeName != "" && m_mazeName.split(" ").length == 1);
	}
	
	/******** Members *******/
	
	String m_mazeName;
	int m_mazeHeight;
	int m_mazeWidth;
	int m_mazeLength;
	
	Label m_nameLabel;
	Label m_heightLabel;
	Label m_widthLabel;
	Label m_lengthLabel;
	Label m_spacer;
	
	Text m_nameText;
	Text m_heightText;
	Text m_widthText;
	Text m_lengthText;
	
	Button m_okButton;
	Button m_cancelButton;

	
}
