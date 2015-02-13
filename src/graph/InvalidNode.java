package graph;

public class InvalidNode extends Node {

	public InvalidNode() {
		cachedValue = null;
		isInvalid = true;
	}
	
	@Override
	public String description() {
		return Node.SYNTAX_ERROR;
	}
	
	@Override
	protected void traverse() {
		return;
	}
}
