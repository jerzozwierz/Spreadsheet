package utilities;

import graph.CellNode;
import graph.ConstNode;
import graph.Graph;
import graph.InnerNode;
import graph.InvalidNode;
import graph.Node;

public class ExpressionParser {
	
	/**
	 * 
	 * @param graph - reference to the whole graph
	 * @param expr - expression. Nulls and empty strings are treated as "0" string
	 */

	private final Graph graph;
	private final String expr;
	private int pos = 0;
	
	public ExpressionParser(Graph graph, String expr) {
		this.graph = graph;
		if (expr == null)
			this.expr = "";
		else
			this.expr = expr;
	}
	
	/**
	 * Formula is considered correct if (and only if) it starts with "=" 
	 * and consists of proper mathematical expression (with constants,
	 * variables and binary operators +, -, *, /).
	 * Variables in aforementioned expression must have form [A-Z][0-9].
	 * They refer to particular cells in spreadsheet.
	 * @return root of the evaluation tree - or InvalidNode instance
	 * in case of an syntax error.
	 */
	public Node parse() {
		if (expr.equals(""))
			return new ConstNode(0.0);
		Node result;
		if (!accept('=')) {
			result = parseN();
		}
		else {
			result = parseS();
		}
		if (!isDone())
			return new InvalidNode();
		
		return result != null ? result : new InvalidNode();
	}
	
	private boolean accept(char c) {
		if (isDone())
			return false;
		if (expr.charAt(pos) == c) {
			++pos;
			return true;
		}
		else
			return false;
	}
	
	private boolean acceptRange(char c1, char c2) {
		if (isDone())
			return false;
		if (expr.charAt(pos) >= c1 && expr.charAt(pos) <= c2) {
			++pos;
			return true;
		}
		else
			return false;
	}
	
	private void undo() {
		--pos;
	}
	
	private boolean isDone() {
		return pos >= expr.length();
	}
	
	private Node parseS() {
		Node lhs = parseP();
		Node rhs = null;
		if (lhs == null)
			return null;
		while (true) {
			if (accept('+')) {
				rhs = parseP();
				if (rhs == null)
					return null;
				lhs = new InnerNode(lhs, OperationType.ADD, rhs);
			}
			else if (accept('-')) {
				rhs = parseP();
				if (rhs == null)
					return null;
				lhs = new InnerNode(lhs, OperationType.SUBTRACT, rhs);
			}
			else
				break;
		}
		return lhs;
	}
	
	private Node parseP() {
		Node lhs = parseT();
		Node rhs = null;
		if (lhs == null)
			return null;
		while (true) {
			if (accept('*')) {
				rhs = parseT();
				if (rhs == null)
					return null;
				lhs = new InnerNode(lhs, OperationType.MULTIPLY, rhs);
			}
			else if (accept('/')) {
				rhs = parseT();
				if (rhs == null)
					return null;
				lhs = new InnerNode(lhs, OperationType.DIVIDE, rhs);
			}
			else
				break;
		}
		return lhs;
		
	}
	
	private Node parseT() {
		Node node = parseC();
		if (node != null)
			return node;
		return parseN();
	}
	
	private Node parseC() {
		if (acceptRange('A', 'Z')) {
			if (acceptRange('1', '9')) {
				return new CellNode(graph, expr.substring(pos - 2, pos));
			}
			else
				undo();
		}
		return null;
	}
	
	private Node parseN() {
		int dotCounter = 0;
		boolean negative = accept('-');
		int begin = pos;
		while (true) {
			if (accept('.')) {
				++dotCounter;
				continue;
			}
			if (acceptRange('0', '9')) {
				continue;
			}
			break;
		}
		if (dotCounter > 1)
			return null;
		if (begin == pos)
			return null;
		double val = Double.parseDouble(expr.substring(begin, pos));
		return negative ? new ConstNode(-val) : new ConstNode(val);
	}
	
}

/*
 * Context-free grammar for allowed expressions:
 * S -> P | P + S | P - S
 * P -> T | T * P | T / P
 * T -> C | N
 * C -> [A-Z][1-9]
 * N -> (decimal number)
 * 
 */
