/**
 * @file HeuristicStateCompartaor.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an heuristic comparator
 * 				
 * @date    30/08/2015
 */

package search;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class implements the comparator (java object)
 *
 * @param <T> the type the comparator work with
 */
public class HeuristicStateComparator<T> implements Comparator<State<T>>, Serializable 
{

	/**
	 * C-Tor
	 * @param heuristic  - the heuristic the compartor work with
	 * @param goalState  - the goal state
	 */
	public HeuristicStateComparator(Heuristic<T> h, State<T> goal) 
	{
		heuristic = h;
		this.goal = goal;
	}

	/**
	 * Override of compare function of Comparator
	 * @param obj1 - the first  state to compare
	 * @param obj2 - the second state to compare
	 * @return the difference between them 
	 */
	@Override
	public int compare(State<T> o1, State<T> o2) 
	{
		// Calculates difference of cost of getting to each state + estimated
		// heuristic
		return (int) ((heuristic.getHeuristicCost(o1, goal) + o1.getCost())
				- (heuristic.getHeuristicCost(o2, goal) + o2.getCost()));
	}

	public void setHeuristic(Heuristic<T> heuristic) 
	{
		this.heuristic = heuristic;
	}

	public void setGoal(State<T> goal) 
	{
		this.goal = goal;
	}
	
	/********* Members ************/
	private static final long serialVersionUID = -5036886696875842387L;

	/** The heuristic. */
	private Heuristic<T> heuristic;
	
	/** The goal. */
	private State<T> goal;

}
