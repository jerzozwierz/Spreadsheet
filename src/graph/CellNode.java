package graph;

public class CellNode extends Node {
	
	private final int x, y; // coordinates
	private final Graph graph;

	public CellNode(Graph graph, int x, int y) {
		this.x = x;
		this.y = y;
		this.graph = graph;
	}
	
	/**
	 * @param graph
	 * @param code - 2-char string of the form [A-Z][1-9]
	 */
	public CellNode(Graph graph, String code) {
		this.graph = graph;
		this.x = (int)(code.charAt(0) - 'A') + 1; // A is 1st row, not 0th
		this.y = (int)(code.charAt(1) - '0');
	}
	
	@Override
	protected void traverse() {
		if (isProcessed) {
			// there is a cycle
			refError = true;
			return;
		}
		Node root = getRoot();
		isProcessed = true;
		root.traverse();
		root.markDependency(this);
		refError |= root.refError;
		isInvalid |= root.isInvalid;
		cachedValue = root.cachedValue;
		isProcessed = false;
	}
	
	private Node getRoot() {
		return graph.getNode(x, y);
	}
	
}
