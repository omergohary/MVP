/**
 * @file CommonSearcher.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an abstract class that contain the common details of all the searchers
 * 				
 * @date    28/08/2015
 */

package search;

import java.util.PriorityQueue;

/**
 * This abstract class has the common logic of all searchers (list, backTrace..)
 *
 * @param <T> the type we work with
 */
public abstract class CommonSearcher<T> implements Searcher<T> 
{
	/**
	 * Instantiates a new common searcher.
	 */
	public CommonSearcher()
	{
		
	}

	@Override
	public abstract Solution<T> search(Searchable<T> s);
	
	 /**
	  * Override of the Searcher's function
	  * @return the data member evaluatedNodes
	  */
	@Override
	public int getNumberOfNodesEvaluated()
	{
		return evaluatedNodes;
	}

	 /**
	  * Pop the priority list and increase the counter
	  * @return the popped state
	  */
	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	 /**
	  * Add new state to the priority list
	  * @param stateToAdd - the state to push
	  */
	protected void addToOpenList(State<T> state) 
	{
		openList.add(state);
	}

	 /**
	  * This function calculates the path from goal state to start state
	  * @param goalState   - the goal state
	  * @param startState  - the start state
	  * @return the path between them (solution object)
	  */
	protected Solution<T> backTrace(State<T> goalState, State<T> startState) 
	{
		Solution<T> s = new Solution<T>();
		State<T> currState = goalState;
		
		while (!startState.equals(currState)) 
		{
			s.addState(currState);
			currState = currState.getCameFrom();
		}
		
		// add final stage (where currState reached startState)
		s.addState(currState);
		return s;
	}
	
	private static final long serialVersionUID = 1L;

	/** The open list. */
	protected PriorityQueue<State<T>> openList;
	
	/** The evaluated nodes. */
	private int evaluatedNodes;
}
