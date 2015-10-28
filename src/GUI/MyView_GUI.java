/**
 * @file MyView_GUI.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a view (in MVP design) that implements a GUI and extends the interface View
 * 				
 * @date    28/10/2015
 */

package GUI;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
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

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;
import view.View;

public class MyView_GUI extends Observable implements View 
{

	public MyView_GUI() 
	{
		m_isMazeSolved = false;
		m_window = new MazeWindow("Maze Puzzle", 500, 300);
	}

	@Override
	public void start() 
	{
		m_window.actualInitWidgets();
		initMazeWindowListeners();
		m_window.run();
	}

	@SuppressWarnings("unchecked")
	public void display()
	{
		if (displayable != null) 
		{
			if (displayable instanceof IMazeDisplayable) 
			{
				IMazeDisplayable mazeDisplayable = (IMazeDisplayable) displayable;
				m_window.setMazeData(mazeDisplayable.getMazeCrossSection());
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
					MessageBoxCreator.createErrorMessageBox(m_window.m_shell, notification);
					break;
				case "generate":
				case "load":
					m_currentMazeName = notification.split(" ")[1].split("'")[1];
					setChanged();
					notifyObservers("get positions " + m_currentMazeName);
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
					m_window.setGoalPositionText("( " + goalLength + ", " + goalHeight + ", " + goalWidth + ")");
				case "move":
					posValues = notification.split(" ");
					int currHeight = Integer.parseInt(posValues[0]);
					int currWidth = Integer.parseInt(posValues[1]);
					int currLength = Integer.parseInt(posValues[2]);
					m_currPosition = new Position(currHeight, currWidth, currLength);
					m_window.setCurrentPositionText("( " + currLength + ", " + currHeight + ", " + currWidth + ")");
					clickSelectedCrossSection();
					checkVictory();
					break;
				case "solve":
					if (m_currPosition.equals(m_startPosition)) 
					{
						m_currentMazeName = notification.split(" ")[1].split("'")[1];
						setChanged();
						notifyObservers("display solution " + m_currentMazeName);
					} 
					else 
					{
						MessageBoxCreator.createErrorMessageBox(m_window.m_shell, "Current position must be the starting position.");
					}
					break;
				case "set_algorithm":
					setAlgorithm(notification);
					break;
				default:
					MessageBoxCreator.createNotificationMessageBox(m_window.m_shell, message);
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

		m_timer.scheduleAtFixedRate(m_task, 0, 400);
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

		if (last.getY() != curr.getY()) 
		{
			retVal = last.getY() > curr.getY() ? SWT.PAGE_DOWN : SWT.PAGE_UP;
		} 
		else if (last.getZ() != curr.getZ()) 
		{
			retVal = last.getZ() > curr.getZ() ? SWT.ARROW_LEFT : SWT.ARROW_RIGHT;
		} 
		else if (last.getX() != curr.getX()) 
		{
			retVal = last.getX() > curr.getX() ? SWT.ARROW_UP : SWT.ARROW_DOWN;
		}

		return retVal;
	}

	private void checkVictory() {
		if (m_currPosition.equals(m_endPosition)) {
			// TODO
			MessageBoxCreator.createMessageBox(m_window.m_shell, SWT.HOME, "Victory!", "Good job!\nMaze completed");
			m_isMazeSolved = true;
		}
	}

	private void initMazeWindowListeners() 
	{
		m_window.setShellCloseListener(new Listener() 
		{
			@Override
			public void handleEvent(Event event) 
			{
				int retVal;
				retVal = MessageBoxCreator.createMessageBox(m_window.m_shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO,
														    "Confirmation", "Are you sure?");
				event.doit = retVal == SWT.YES;
				if (event.doit) 
				{
					setChanged();
					notifyObservers("EXIT");
				}
			}
		});

		m_window.setGenerateSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeDialog dgd = new GenerateMazeDialog(m_window.m_shell);
				int retVal;
				retVal = dgd.open();
				if (retVal == SWT.OK) {
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

		m_window.setSolveSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				if (m_currentMazeName != null && currAlgorithm != null) 
				{
					setChanged();
					notifyObservers("solve " + m_currentMazeName + " " + currAlgorithm);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_window.setHelpHintItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				MessageBoxCreator.createErrorMessageBox(m_window.m_shell, "Not yet implemented!");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_window.setHelpAboutItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				MessageBoxCreator.createNotificationMessageBox(m_window.m_shell, "About",
						"Created by: Omer Gohary\nCourse: Algorithmic Java Development\nLecturer: Nissim B.");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_window.setFilePropertyItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(m_window.m_shell, SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				m_propertiesFile = fd.open();
				if (m_propertiesFile != null) 
				{
					setChanged();
					notifyObservers("PROPERTY: " + m_propertiesFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_window.setFileSaveItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(m_window.m_shell, SWT.SAVE);
				fd.setText("save");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				m_saveMazeFile = fd.open();
				if (m_saveMazeFile != null) 
				{
					File file = new File(m_saveMazeFile);
					String path = file.getParentFile().getAbsolutePath();
					String fileName = path + File.separator + m_currentMazeName + ".maz";
					setChanged();
					notifyObservers("save maze " + m_currentMazeName + " " + fileName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_window.setFileLoadItemSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				FileDialog fd = new FileDialog(m_window.m_shell, SWT.OPEN);
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

		m_window.setXSectionRadioSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_selectedCrossSection = m_window.xSectionButton;
				
				if (m_currentMazeName != null) 
				{
					m_window.setCharacterPosition(m_currPosition.getY(), m_currPosition.getZ());
					setChanged();
					notifyObservers("display cross section by X " + m_currPosition.getX() + " for " + m_currentMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});

		m_window.setYSectionRadioSelectionListener(new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_selectedCrossSection = m_window.ySectionButton;
				
				if (m_currentMazeName != null) 
				{
					m_window.setCharacterPosition(m_currPosition.getX(), m_currPosition.getZ());
					setChanged();
					notifyObservers("display cross section by Y " + m_currPosition.getY() + " for " + m_currentMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
			}
		});

		m_window.setZSectionRadioSelectionListener(new SelectionListener() 
		{

			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_selectedCrossSection = m_window.zSectionButton;
				if (m_currentMazeName != null) 
				{
					m_window.setCharacterPosition(m_currPosition.getX(), m_currPosition.getY());
					setChanged();
					notifyObservers("display cross section by Z " + m_currPosition.getZ() + " for " + m_currentMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		m_window.setMazeDisplayerKeyListener(new KeyListener() 
		{
			@Override
			public void keyReleased(KeyEvent arg0) {
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

				if (direction != "" && m_currentMazeName != null && m_currPosition != null && !m_isMazeSolved)
				{
					setChanged();
					notifyObservers("move direction " + direction + " " + m_currentMazeName + " " + m_currPosition.getX()
							+ " " + m_currPosition.getY() + " " + m_currPosition.getZ());
				}
			}
		});

		SelectionListener exitSelectionListener = new SelectionListener() 
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				m_window.m_shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				
			}
		};

		m_window.setExitSelectionListener(exitSelectionListener);

		m_window.setFileExitItemSelectionListener(exitSelectionListener);

		// Default select YSection:
		m_selectedCrossSection = m_window.ySectionButton;
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
		m_window.maze.getDisplay().syncExec(new Runnable() 
		{
			@Override
			public void run() 
			{
				Event e = new Event();
				e.keyCode = keyCode;
				m_window.maze.notifyListeners(SWT.KeyDown, e);
			}
		});
	}
	
	/***************** MEMBERS ****************/
	
	/** The window that represent the maze to user **/
	MazeWindow m_window;

	/** Files **/
	String m_propertiesFile;
	String m_saveMazeFile;
	String m_loadMazeFile;
	String m_currentMazeName;

	/** Maze positions **/
	Position m_startPosition;
	Position m_currPosition;
	Position m_endPosition;

	/** Buttons and Images **/
	Button m_selectedCrossSection;
	Image m_victoryImage;

	/** Flags **/
	boolean m_isMazeSolved;

	/** GUI's issues **/
	Timer m_timer;
	TimerTask m_task;
	
	
	@Override
	public void SetCommandsContainer(HashMap<String, Command> mapToSet) 
	{
		/** NOT NEEDED IN MVP **/
	}

	@Override
	public void Print(String stringToPrint) 
	{

	}

	@Override
	public void shutDown() throws IOException 
	{

	}
}
