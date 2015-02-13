package graph;

import java.util.HashSet;
import java.util.Set;

public abstract class Node {
	
	public static final String REFERENCE_ERROR = "#REF_ERROR";
	public static final String SYNTAX_ERROR = "#SYNTAX_ERROR";
	
	protected Set<Node> dependOnMe = new HashSet<Node>();
	protected Double cachedValue = null;
	protected boolean isProcessed = false; // won't work with multiple threads!
	protected boolean isInvalid = false;
	protected boolean refError = false;
	
	protected abstract void traverse();
	
	/**
	 * Invariant: after calling getValue, node gets clean
	 * @return null if something's wrong, computed value otherwise
	 *  
	 */
	public Double getValue() {
		if (isDirty())
			traverse();
		return cachedValue;
	}
	
	public String description() {
		Double value = getValue();
		if (value != null)
			return value.toString();
		else
			return REFERENCE_ERROR; // the node depends on an uncomputable one
		// notice: derived classes might override this method
	}
		
	protected boolean isDirty() {
		return cachedValue == null &&
				!isInvalid && !refError;
	}
	
	/**
	 * Use before removing/updating a node. This will make all nodes
	 * depending on this node to recompute their values (possibly later)
	 * 
	 * We assume that the program consists of main thread only.
	 */
	protected void invalidateReferences() {
		if (isProcessed)
			return;
		isProcessed = true;
		dirtify();
		for (Node nd : dependOnMe)
			nd.invalidateReferences();
		dependOnMe = new HashSet<Node>();
		isProcessed = false;
	}
	
	/**
	 * Use it after calling traverse!
	 * @param what - parent of the node
	 */
	protected void markDependency(Node what) {
		dependOnMe.add(what);
	}
	
	private void dirtify() {
		cachedValue = null;
		isInvalid = false;
		refError = false;
	}
	
}


