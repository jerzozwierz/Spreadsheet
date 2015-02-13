package graph;

import main.Spreadsheet;
import utilities.ExpressionParser;

public class Graph {
	
	/*
	 * there are no null pointers in the array.
	 */
	private Node[][] roots = new Node[Spreadsheet.ROWS][Spreadsheet.COLUMNS];
	
	public Graph(String[][] expressions) {
		if (expressions.length != Spreadsheet.ROWS)
			throw new IllegalArgumentException();
		for (int i = 0; i < Spreadsheet.ROWS; ++i) {
			if (expressions[i].length != Spreadsheet.COLUMNS)
				throw new IllegalArgumentException();
			for (int j = 0; j < Spreadsheet.COLUMNS; ++j) {
				ExpressionParser parser = 
						new ExpressionParser(this, expressions[i][j]);
				roots[i][j] = parser.parse();
			}
		}
	}
	
	public Node getNode(int i, int j) {
		return roots[i-1][j-1];
	}
	
	public String getDescription(int i, int j) {
		return roots[i-1][j-1].description();
	}
	
	public void substitute(int i, int j, String expression) {
		roots[i-1][j-1].invalidateReferences();
		ExpressionParser parser = new ExpressionParser(this, expression);
		roots[i-1][j-1] = parser.parse();
	}
		
}
