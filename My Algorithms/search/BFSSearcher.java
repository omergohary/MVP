/**
 * @file BFS.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an example of searcher - Best-First-Search (BFS).
 * 				
 * @date    27/08/2015
 */

package search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * This class is a type of searcher, calls BFS, that implement the "search" function of the abstract father - Searcher
 * @param <T>
 */
public class BFSSearcher<T> extends CommonSearcher<T> 
{

	private static final long serialVersionUID = -6545878451575448027L;

	/**
	 * Instantiates a new BFS searcher.
	 */
	public BFSSearcher() 
	{
		initDefaultList();
	}
	
	/**
	 * C-Tor 
	 */
	public BFSSearcher(int num) 
	{
		initDefaultList();
	}

	public void initDefaultList() {
		openList = new PriorityQueue<State<T>>(100000, new Comparator<State<T>>() 
		    {
			public int compare(State<T> o1, State<T> o2) {
				return (int) (o1.getCost() - o2.getCost());
			}
		});
	}

	/**
	 * This function is responsible to act the BFS algorithm itself
	 * @param s - the searchable object to search on.
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		addToOpenList(s.getStartState());
		HashSet<State<T>> closedSet = new HashSet<State<T>>();

		while (openList.size() > 0) {
			State<T> n = popOpenList();// dequeue
			closedSet.add(n);

			if (n.equals(s.getGoalState()))
				return backTrace(n, s.getStartState());
			// private method, back traces through the parents

			ArrayList<State<T>> successors = s.getAllPossibleStates(n); // however
																		// it is
																		// implemented
			for (State<T> state : successors) {
				state.setCameFrom(n);
				state.setCost(n.getCost() + 1);
				if (!(closedSet.contains(state)) && !(openList.contains(state))) {
					addToOpenList(state);
				} else {
					Collection<State<T>> col;
					boolean openHas = openList.contains(state);

					// Checks who contains the state in question
					if (openHas) {
						col = openList;
					} else {
						col = closedSet;
					}

					Iterator<State<T>> it = col.iterator();

					while (it.hasNext()) {
						State<T> curr = it.next();
						if (state.equals(curr)) {
							if (state.getCost() < curr.getCost()) {
								if (!openHas) { // If it's not in the opened
												// list, add the state with the
												// better cost to the open list.
									openList.add(state);
								} else { // if it's already in open list, adjust
											// it's cost and set parent
									it.remove();
									col.add(curr);
								}
							}
						}
					}
				}
			}
		}

		// If reached here, means loop finished going through the openList, and
		// no state was equal to the goalState.
		return null;
	}

}
