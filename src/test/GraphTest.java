package test;

import static org.junit.Assert.fail;
import graph.Graph;
import graph.Node;

import org.junit.Test;

public class GraphTest {

	String[][] input = new String[26][9];
	{
		for (int i = 0; i < 26; ++i) {
			for (int j = 0; j < 9; ++j)
				input[i][j] = "";
		}
	}
	
	@Test
	public void test1Cycles() {
		input[0][0] = "=A2";
		input[0][1] = "=A3";
		input[0][2] = "=A4";
		input[0][3] = "=A4";
		Graph graph = new Graph(input);
		graph.getNode(1, 1).getValue();
		if (!graph.getDescription(1, 1).equals(Node.REFERENCE_ERROR))
			fail();
	}
	
	@Test
	public void test1IncorrectInput() {
		input[0][0] = "=A2+A3";
		input[0][1] = "xxx";
		input[0][2] = "=A3";
		//Graph graph = new Graph(input);
		//TODO
		
	}
	
	@Test
	public void test1Computing() {
		String xs = "0.004", ys = "45.1233", zs = "-23.4";
		Double x = 0.004, y = 45.1233, z = 23.4;
		input[0][0] = "=" + xs + "+" + zs + "/" + ys + "*" + zs + "*" + xs;
		Double result = x+z/y*z*x;
		Graph graph = new Graph(input);
		if (graph.getNode(1, 1).getValue() - result > 0.0000000000001)
			fail();
	}
	
	@Test
	public void test1Substituting() {
		input[0][0] = "=A2";
		Graph graph = new Graph(input);
		if (graph.getNode(1, 1).getValue() != 0)
			fail("first");
		graph.substitute(1, 2, "5.0");
		if (graph.getNode(1, 1).getValue() != 5.0)
			fail("second");
	}
	
	@Test
	public void test2Substituting() {
		input[0][0] = "=A2";
		input[0][1] = "wrong";
		Graph graph = new Graph(input);
		graph.getNode(1, 1).getValue();
		graph.substitute(1, 2, "5.0");
		System.out.println(graph.getNode(1, 1).description());
		if (graph.getNode(1, 1).getValue() != 5.0)
			fail();
		graph.substitute(1, 2, "=A3");
		graph.substitute(1, 3, "=A1");
		if (!graph.getNode(1, 1).description().equals(Node.REFERENCE_ERROR))
			fail();
		graph.substitute(2, 1, "=B3+A1");
		if (!graph.getNode(2, 1).description().equals(Node.REFERENCE_ERROR))
			fail();
		graph.substitute(1, 3, "=-1");
		if (graph.getNode(2, 1).getValue() != -1)
			fail();
	}

}
