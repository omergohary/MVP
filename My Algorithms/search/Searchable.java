/**
 * @file Searchable.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface for a search domain
 * 				
 * @date    27/08/2015
 */

package search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Interface Searchable
 *
 * @param <T> the type of Searchable
 */
public interface Searchable<T> extends Serializable 
{
	
	/**
	 * Get the start state
	 *
	 * @return the start state object
	 */
	State<T> getStartState();

	/**
	 * Gets the goal state.
	 *
	 * @return the goal state
	 */
	State<T> getGoalState();

	/**
	 * Get the all possible states
	 *
	 * @param s - the required state to check
	 * 
	 * @return an array of the all possible states
	 */
	ArrayList<State<T>> getAllPossibleStates(State<T> s);
}
