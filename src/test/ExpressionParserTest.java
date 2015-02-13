package test;

import static org.junit.Assert.fail;
import graph.InvalidNode;
import graph.Node;

import org.junit.Test;

import utilities.ExpressionParser;

public class ExpressionParserTest {

	@Test
	public void test1() {
		String[] correct = {
				"5.12333",
				"=C1+C1*C1/23*D4",
				"=2+-2*2",
				"=Z9+C2*C2",
				"687888888222.9123994399293499433",
				"=C1+A5*F9/0.05+C1",
				""
		};
		String[] incorrect = {
				"=C1A2",
				"=4++4",
				"2+2",
				"=C1+C2+C3-C0",
				"=thisdoesntmakesense",
				"=C3+seven",
				"=****",
				"=2.2.2.2.2.2.2",
				"4.4.",
				"=C11"
		};
		for (String ex : correct) {
			Node nd = new ExpressionParser(null, ex).parse();
			if (nd instanceof InvalidNode)
				fail();
		}
		for (String ex : incorrect) {
			Node nd = new ExpressionParser(null, ex).parse();
			if (!(nd instanceof InvalidNode))
				fail();
		}
	}
}
