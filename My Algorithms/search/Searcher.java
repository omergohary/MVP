/**
 * @file Searcher.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents an interface for searcher.
 * 				
 * @date    27/08/2015
 */

package search;

import java.io.Serializable;

/**
 * This interface contains the expected common functionality to any searcher.
 */
public interface Searcher<T> extends Serializable 
{
	
	/**
	 * The search method
	 * @param s - the searchable object to search on.
	 * @return - the solution (using template)
	 */
    public Solution<T> search(Searchable<T> s);
    
    /**
     * get how many nodes were evaluated by the algorithm
     * @return the number of nodes evaluated
     */
    // get how many nodes were evaluated by the algorithm
    public int getNumberOfNodesEvaluated();

}
