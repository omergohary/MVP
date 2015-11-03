/**
 * @file AStarSearcher.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an example of searcher that a type of Best-First-Search - it calls A Star.
 * 				
 * @date    30/08/2015
 */

package search;

import java.util.PriorityQueue;

import search.Heuristic;

/**
 * The A Star class that extends the BFS searcher
 * 
 * @param <T> the type of template
 */
public class AstarSearcher<T> extends BFSSearcher<T> 
{

	public AstarSearcher() 
	{
		
	}
	
	/**
	 * C-Tor
	 * @param m_heuristic - the heuristic the A star needs to work with
	 */
	public AstarSearcher(Heuristic<T> h)
	{
		m_heuristic = h;
	}

	/**
	 * Set the heuristic
	 *
	 * @param heuristic - new heuristic to set
	 * @return boolean - true if succeeded, false otherwise
	 */
	public boolean setHeuristic(Heuristic<T> heuristic) 
	{
		if (heuristic != null)
		{
			this.m_heuristic = heuristic;
			return true;
		}
		return false;
	}

	/**	
	 *  This function is an override to the search of the BFS searcher
	 */
	@Override
	public Solution<T> search(Searchable<T> s) 
	{
		if (m_heuristic != null) {
			openList = new PriorityQueue<>(100000, new HeuristicStateComparator<>(m_heuristic, s.getGoalState()));
			return super.search(s);
		}
		
		return null;
	}
	
	/*********** Members ************/
	
	/** The heuristic data member **/
	private static final long serialVersionUID = -6332645588872909455L;

	private Heuristic<T> m_heuristic;
}
