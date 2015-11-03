/**
 * @file State.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a single state of the problem - using template
 * 				
 * @date    27/08/2015
 */

package search;

import java.io.Serializable;

/**
 * This class represents the state
 * 
 * @param <T> The generic type of state
 */
public class State<T> implements Serializable
{

	public State() 
	{
		cost=0;
	}

	/**
	 * C-Tor
	 * @param state - type of state
	 */
	public State(T state)
	{ 
		this.state = state;
		cost = 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State<T> other = (State<T>) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	/**
	 * Sets the came-from data member.
	 *
	 * @param state - value to set
	 */
	public void setCameFrom(State<T> state) 
	{
		this.cameFrom = state;
	}

	/**
	 * Gets the came from.
	 *
	 * @return the came-from data member
	 */
	public State<T> getCameFrom() 
	{
		return this.cameFrom;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(double cost) 
	{
		this.cost = cost;
	}
	
	public void setState(T aState)
	{
		this.state = aState;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() 
	{
		return cost;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public T getState()	
	{
		return state;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	
	public void print()
	{
		System.out.println(state.toString());
	}
	
	public String toString() 
	{
		return state.toString();
	}
	
	private static final long serialVersionUID = 550240802931656281L;

	/** The state. */
	private T state;           // the state represented
	
	/** The cost. */
	private double cost;       // cost to reach this state
	
	/** The came from. */
	private State<T> cameFrom; // the state we came from to this state
}
