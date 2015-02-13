package graph;

import utilities.OperationType;

public class InnerNode extends Node {
	
	private final Node lhs, rhs;
	private final OperationType type;
	
	public InnerNode(Node lhs, OperationType type, Node rhs) {
		this.lhs = lhs;
		this.type = type;
		this.rhs = rhs;
	}

	@Override
	protected void traverse() {
		if (isProcessed) {
			// there is a cycle
			refError = true;
			return;
		}
		isProcessed = true;
		if (lhs.isDirty()) {
			lhs.traverse();
		}
		lhs.markDependency(this);
		if (rhs.isDirty())
			rhs.traverse();
		rhs.markDependency(this);
		isInvalid |= lhs.isInvalid | rhs.isInvalid;
		refError |= lhs.refError | rhs.refError;
		if (lhs.cachedValue == null || rhs.cachedValue == null)
			cachedValue = null;
		else
			cachedValue = type.apply(lhs.cachedValue, rhs.cachedValue);
		isProcessed = false;		
	}
	
}