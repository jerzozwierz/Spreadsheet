package test;

import static org.junit.Assert.*;
import main.Spreadsheet;

import org.junit.Test;

public class SpreadsheetTest {

	String[][] input = new String[26][9];
	{
		for (int i = 0; i < 26; ++i) {
			for (int j = 0; j < 9; ++j)
				input[i][j] = "";
		}
	}
	
	@Test
	public void test1Simple() {
		input[0][0] = "2.3";
		input[0][1] = "5.4";
		input[0][2] = "=A1+A2";
		Spreadsheet sheet = new Spreadsheet(input);
		if (sheet.getValue(1, 1) != 2.3)
			fail();
		if (sheet.getValue(1, 3) != 2.3 + 5.4)
			fail();
	}
	
	@Test
	public void test1Cycles() {
		input[0][0] = "=A1";
		Spreadsheet sheet = new Spreadsheet(input);
		if (!((Double)sheet.getValue(1, 1)).toString().equals("NaN"))
			fail();
		input[0][0] = "=A2+A3";
		input[0][1] = "=A3+A5";
		input[0][2] = "=A1*5.66";
		sheet = new Spreadsheet(input);
		if (!((Double)sheet.getValue(1, 1)).toString().equals("NaN"))
			fail();
	}
	
	@Test
	public void test2Cycles() {
		input[0][0] = "=A3";
		input[0][1] = "=A3+A3";
		input[0][2] = "=5.00+A4";
		// A4 = 0 ?
		Spreadsheet sheet = new Spreadsheet(input);
		if (sheet.getValue(1, 2) != 10.0)
			fail();
	}
	
	@Test
	public void test1IncorrectInput() {
		input[0][0] = "=A2";
		input[0][1] = "=nothing";
		Spreadsheet sheet = new Spreadsheet(input);
		if (!((Double)sheet.getValue(1, 1)).toString().equals("NaN"))
			fail();
	}

}
