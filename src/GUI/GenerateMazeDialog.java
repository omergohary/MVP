/**
 * @file GenerateMazeDialog.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a class that responsible to generate a maze's dialog
 * 				
 * @date    28/10/2015
 */
package GUI;

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
		//shell.setSize(300, 200);
		m_shell.setText("Generate maze");

		nameLabel = new Label(m_shell, SWT.NULL);
		nameLabel.setText("Maze Name:");
		nameLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		nameText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		heightLabel = new Label(m_shell, SWT.NULL);
		heightLabel.setText("Maze Height:");
		heightLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		heightText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		heightText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		widthLabel = new Label(m_shell, SWT.NULL);
		widthLabel.setText("Maze Width:");
		widthLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		widthText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		widthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		lengthLabel = new Label(m_shell, SWT.NULL);
		lengthLabel.setText("Maze Length:");
		lengthLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		lengthText = new Text(m_shell, SWT.SINGLE | SWT.BORDER);
		lengthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		spacer = new Label(m_shell, SWT.NONE);
		spacer.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));

		okButton = new Button(m_shell, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));
		okButton.addSelectionListener(new SelectionListener() {

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
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		cancelButton = new Button(m_shell, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));
		cancelButton.addSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {
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
		return mazeName;
	}

	public void setMazeName(String aMazeName)
	{
		this.mazeName = aMazeName;
	}

	public int getHeight()
	{
		return mazeHeight;
	}

	public void setHeight(int height)
	{
		this.mazeHeight = height;
	}

	public int getWidth()
	{
		return mazeWidth;
	}

	public void setWidth(int width)
	{
		this.mazeWidth = width;
	}

	public int getLength() 
	{
		return mazeLength;
	}

	public void setLength(int length) 
	{
		this.mazeLength = length;
	}

	private boolean trySetMembers() 
	{
		try 
		{
			this.mazeHeight = Integer.parseInt(heightText.getText());
			this.mazeLength = Integer.parseInt(lengthText.getText());
			this.mazeWidth  = Integer.parseInt(widthText.getText());
		} 
		catch (NumberFormatException e)
		{
			return false;
		}
		
		mazeName = nameText.getText();
		return (mazeName!=null && mazeName != "" && mazeName.split(" ").length == 1);
	}

	/***************** Members ***************/
	
	String mazeName;
	int mazeHeight;
	int mazeWidth;
	int mazeLength;

	Label nameLabel;
	Label heightLabel;
	Label widthLabel;
	Label lengthLabel;
	
	Label spacer;

	Text nameText;
	Text heightText;
	Text widthText;
	Text lengthText;

	Button okButton;
	Button cancelButton;
}
