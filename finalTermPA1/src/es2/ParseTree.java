package es2;
import java.util.*;
public class ParseTree {
	public Token node;
	public ArrayList<ParseTree> children = new ArrayList<ParseTree>();	
	public ParseTree () {}
	public ParseTree (Token t) {this.node = t;}	
	public void print() { print("", true); }
	private void print(String prefix, boolean isTail) {
		System.out.println(prefix + "|__" + node.toString());
		for (Iterator<ParseTree> it = children.iterator(); it.hasNext(); )
			it.next().print(prefix + (isTail ? "    " : "|   "), !it.hasNext());
	}
}
