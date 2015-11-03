/**
 * @file MyObservableGUI.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a view (in MVP design) that implements a GUI and extends the interface View
 * 				
 * @date    28/10/2015
 */

package gui;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenerators.Position;
import search.Solution;
import search.State;
import view.IDisplayable;
import view.IMazeDisplayable;
import view.ISolutionDisplayable;
import view.view_MVP;

public class MyObservableGUI extends view_MVP 
{

	/** C-Tor **/
	public MyObservableGUI() 
	{
		m_isMazeSolved = false;
		m_win 		 = new MazeWindow("Maze Puzzle", 500, 300);
	}

	@Override
	public void start() 
	{
		m_win.actualInitWidgets();
		initMazeWindowListeners();
		m_win.run();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void display(IDisplayable displayable) 
	{
		if (displayable != null) 
		{
			if (displayable instanceof IMazeDisplayable) 
			{
				IMazeDisplayable mazeDisplayable = (IMazeDisplayable) displayable;
				m_win.setMazeData(mazeDisplayable.getMazeCrossSection());
			} 
			
			else if (displayable instanceof ISolutionDisplayable<?>) 
			{
				ISolutionDisplayable<Position> positionSolution = (ISolutionDisplayable<Position>) displayable;
				Solution<Position> solution = positionSolution.getSolution();
				solveMaze(solution);
			} 
			else 
			{
				String message = displayable.getMessage().toLowerCase();
				String[] splitMessage = message.split(":");
				String reason = splitMessage[0];
				String notification = "";
				
				if (splitMessage.length > 1) 
				{
					notification = splitMessage[1].trim();
				}

				switch (reason) 
				{
				case "exception":
					MessageBoxCreator.createErrorMessageBox(m_win.shell, notification);
					break;
				case "generate":
				case "load":
					m_currMazeName = notification.split(" ")[1].split("'")[1];
					setChanged();
					notifyObservers("get positions " + m_currMazeName);
					m_isMazeSolved = false;
					break;
				case "positions":
					String[] posValues = notification.split(" ");
					int startHeight = Integer.parseInt(posValues[0]);
					int startWidth = Integer.parseInt(posValues[1]);
					int startLength = Integer.parseInt(posValues[2]);
					m_startPosition = new Position(startHeight, startWidth, startLength);
					int goalHeight = Integer.parseInt(posValues[3]);
					int goalWidth = Integer.parseInt(posValues[4]);
					int goalLength = Integer.parseInt(posValues[5]);
					m_endPosition = new Position(goalHeight, goalWidth, goalLength);
					m_win.setGoalPositionText("( " + goalLength + ", " + goalHeight + ", " + goalWidth + ")");
				case "move":
					posValues = notification.split(" ");
					int currHeight = Integer.parseInt(posValues[0]);
					int currWidth = Integer.parseInt(posValues[1]);
					int currLength = Integer.parseInt(posValues[2]);
					m_currPosition = new Position(currHeight, currWidth, currLength);
					m_win.setCurrentPositionText("( " + currLength + ", " + currHeight + ", " + currWidth + ")");
					clickSelectedCrossSection();
					checkVictory();
					break;
				case "solve":
					m_currMazeName = notification.split(" ")[1].split("'")[1];
					setChanged();
					notifyObservers("display solution " + m_currMazeName + " " + m_currPosition.getLength() + " " 
					+ m_currPosition.getHeight() + " " + m_currPosition.getWidth());
					break;
				case "set_algorithm":
					setAlgorithm(notification);
					break;
				default:
					MessageBoxCreator.createNotificationMessageBox(m_win.shell, message);
					break;
				}
			}
		}
	}

	private void solveMaze(Solution<Position> solution) 
	{
		final LinkedList<Integer> eventList = getKeyMoveList(solution.getSolutionList());
		m_timer = new Timer();
		m_task = new TimerTask() 
		{
			boolean allKeysClicked = false;
			@Override
			public void run() 
			{
				if (eventList.isEmpty()) 
				{
					allKeysClicked = true;
				} 
				else 
				{
					simulateKeyPress(eventList.pop());
				}

				if (allKeysClicked) 
				{
					closeTimerTask();
				}
			}
		};

		m_timer.scheduleAtFixedRate(m_task, 0, 300);
	}
	
	protected void closeTimerTask() 
	{
		m_task.cancel();
		m_timer.cancel();
	}

	private LinkedList<Integer> getKeyMoveList(LinkedList<State<Position>> solutionList) 
	{
		LinkedList<State<Position>> copySolutionList = new LinkedList<>();
		copySolutionList.addAll(solutionList);
		Collections.reverse(copySolutionList);
		LinkedList<Integer> eventList = new LinkedList<>();
		Position last = copySolutionList.pop().getState();
		int swtMove;

		for (State<Position> state : copySolutionList) 
		{
			swtMove = getEventDiff(last, state.getState());
			eventList.add(swtMove);
			last = state.getState();
		}

		return eventList;
	}

	private Integer getEventDiff(Position last, Position curr)
	{
		Integer retVal = 0;

		if (last.getHeight() != curr.getHeight()) 
		{
			retVal = last.getHeight() > curr.getHeight() ? SWT.PAGE_DOWN : SWT.PAGE_UP;
		} 
		
		else if (last.getWidth() != curr.getWidth()) 
		{
			retVal = last.getWidth() > curr.getWidth() ? SWT.ARROW_LEFT : SWT.ARROW_RIGHT;
		} 
		
		else if (last.getLength() != curr.getLength()) 
		{
			retVal = last.getLength() > curr.getLength() ? SWT.ARROW_UP : SWT.ARROW_DOWN;
		}

		return retVal;
	}

	private void checkVictory() 
	{
		if (m_currPosition.equals(m_endPosition)) 
		{
			MessageBoxCreator.createMessageBox(m_win.shell, SWT.HOME, "Victory!", "Good job!\nMaze completed");
			m_isMazeSolved = true;
		}
	}

	private void initMazeWindowListeners() 
	{
		m_win.setShellCloseListener(new Listener() 
		{
			@Override
			public void handleEvent(Event event) 
			{
				int retVal;
				retVal = MessageBoxCreator.createMessageBox(m_win.shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO,
						"Confirmation", "Are you sure?");
				event.doit = retVal == SWT.YES;
				if (event.doit) 
				{
					setChanged();
					notifyObservers("EXIT");
				}
			}
		});

		m_win.setGenerateSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				GenerateMazeDialog dgd = new GenerateMazeDialog(m_win.shell);
				int retVal;
				retVal = dgd.open();
				if (retVal == SWT.OK) 
				{
					setChanged();
					notifyObservers("generate 3d maze " + dgd.getMazeName() + " " + dgd.getHeight() + " "
							+ dgd.getWidth() + " " + dgd.getLength());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_win.setSolveSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (m_currMazeName != null && currAlgorithm != null) 
				{
					setChanged();
					notifyObservers("solve " + m_currMazeName + " " + currAlgorithm + " " + m_currPosition.getLength()
					+ " " + m_currPosition.getHeight() + " " + m_currPosition.getWidth());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_win.setHelpHintItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				MessageBoxCreator.createErrorMessageBox(m_win.shell, "Not yet implemented!");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_win.setHelpAboutItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				MessageBoxCreator.createNotificationMessageBox(m_win.shell, "About",
						"Created by: Omer Gohary\nCourse: Algorithmic Java Development\nLecturer: Nissim B.");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_win.setFilePropertyItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(m_win.shell, SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				m_propertyFile = fd.open();
				if (m_propertyFile != null) 
				{
					setChanged();
					notifyObservers("PROPERTY: " + m_propertyFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_win.setFileSaveItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(m_win.shell, SWT.SAVE);
				fd.setText("save");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				m_saveMazeFile = fd.open();
				
				if (m_saveMazeFile != null) 
				{
					File file = new File(m_saveMazeFile);
					String path = file.getParentFile().getAbsolutePath();
					String fileName = path + File.separator + m_currMazeName + ".maz";

					setChanged();
					notifyObservers("save maze " + m_currMazeName + " " + fileName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_win.setFileLoadItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(m_win.shell, SWT.OPEN);
				fd.setText("load");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				m_loadMazeFile = fd.open();
				
				if (m_loadMazeFile != null) 
				{
					File file = new File(m_loadMazeFile);
					String fileName = file.getName();
					String mazeName = fileName;
					int pos = fileName.lastIndexOf(".");
					
					if (pos > 0) 
					{
						mazeName = fileName.substring(0, pos);
					}
					setChanged();
					notifyObservers("load maze " + m_loadMazeFile + " " + mazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_win.setXSectionRadioSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_selectedCrossSection = m_win.xSectionButton;
				
				if (m_currMazeName != null) 
				{
					m_win.setCharacterPosition(m_currPosition.getHeight(), m_currPosition.getWidth());
					setChanged();
					notifyObservers("display cross section by X " + m_currPosition.getLength() + " for " + m_currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_win.setYSectionRadioSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				m_selectedCrossSection = m_win.ySectionButton;
				if (m_currMazeName != null) 
				{
					m_win.setCharacterPosition(m_currPosition.getLength(), m_currPosition.getWidth());
					setChanged();
					notifyObservers("display cross section by Y " + m_currPosition.getHeight() + " for " + m_currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_win.setZSectionRadioSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_selectedCrossSection = m_win.zSectionButton;
				if (m_currMazeName != null) 
				{
					m_win.setCharacterPosition(m_currPosition.getLength(), m_currPosition.getHeight());
					setChanged();
					notifyObservers("display cross section by Z " + m_currPosition.getWidth() + " for " + m_currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});

		m_win.setMazeDisplayerKeyListener(new KeyListener()
		{
			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				String direction = "";
				switch (e.keyCode) 
				{
				case SWT.ARROW_UP:
					direction = "backward";
					break;
				case SWT.ARROW_DOWN:
					direction = "forward";
					break;
				case SWT.ARROW_LEFT:
					direction = "left";
					break;
				case SWT.ARROW_RIGHT:
					direction = "right";
					break;
				case SWT.PAGE_UP:
					direction = "up";
					break;
				case SWT.PAGE_DOWN:
					direction = "down";
					break;
				default:
					break;
				}

				if (direction != "" && m_currMazeName != null && m_currPosition != null && !m_isMazeSolved) 
				{
					setChanged();
					notifyObservers("move direction " + direction + " " + m_currMazeName + " " + m_currPosition.getHeight()
							+ " " + m_currPosition.getWidth() + " " + m_currPosition.getLength());
				}
			}
		});

		SelectionListener exitSelectionListener = new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_win.shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				
			}
		};

		m_win.setExitSelectionListener(exitSelectionListener);

		m_win.setFileExitItemSelectionListener(exitSelectionListener);

		// Default select YSection:
		m_selectedCrossSection = m_win.ySectionButton;
		clickSelectedCrossSection();
	}

	private void clickSelectedCrossSection() 
	{
		m_selectedCrossSection.getDisplay().syncExec(new Runnable() 
		{
			@Override
			public void run() 
			{
				m_selectedCrossSection.setSelection(true);
				m_selectedCrossSection.notifyListeners(SWT.Selection, new Event());
			}
		});
	}

	private void simulateKeyPress(final int keyCode) 
	{
		m_win.maze.getDisplay().syncExec(new Runnable() 
		{
			@Override
			public void run() 
			{
				Event e = new Event();
				e.keyCode = keyCode;
				m_win.maze.notifyListeners(SWT.KeyDown, e);
			}
		});
	}
	
	/******** Members *********/
	
	MazeWindow m_win;

	String m_propertyFile;
	String m_saveMazeFile;
	String m_loadMazeFile;

	String m_currMazeName;

	Position m_startPosition;
	Position m_currPosition;
	Position m_endPosition;

	Button m_selectedCrossSection;

	boolean m_isMazeSolved;
	Image m_victoryImage;

	Timer m_timer;
	TimerTask m_task;
}
