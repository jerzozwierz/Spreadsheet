package graph;

public class ConstNode extends Node {

	/*
	 * note that ConstNode is never dirty
	 * prevents to create a null-valued node
	 */
	public ConstNode(double value) {
		cachedValue = value;
	}
	
	@Override
	public Double getValue() {
		return cachedValue;
	}
	
	@Override
	protected void traverse() {
		return;
	}
	
}
