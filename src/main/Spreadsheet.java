package main;

import graph.Graph;

public class Spreadsheet {
	
	public static final int ROWS = 26, COLUMNS = 9;
	
	private Graph dependencies;
	
	public Spreadsheet(String[][] spreadsheetCells) {
		dependencies = new Graph(spreadsheetCells);
	}
	
	public double getValue(int row, int column) {
		if (row < 1 || row > ROWS || column < 1 || column > COLUMNS)
			throw new IndexOutOfBoundsException();
		Double result = dependencies.getNode(row, column).getValue();
		return result != null ? result : Double.NaN;
	}
	
	public String getVisibleValue(int row, int column) {
		if (row < 1 || row > ROWS || column < 1 || column > COLUMNS)
			throw new IndexOutOfBoundsException();
		return dependencies.getDescription(row, column);
	}
	
	public void setValue(int row, int column, String newValue) {
		dependencies.substitute(row, column, newValue);
	}
	
}
