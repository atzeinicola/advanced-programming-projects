package es2;
public class Token {	
	public final int tag;
	public final static int ID=256, LITERAL=257, GRAPH=258, DIGRAPH=259, 
	LABEL=260, STYLE=261, DOTTED=262, NTERMINAL=263, EOF='$';	
	public Token (int tag) {this.tag = tag;}
	public String toString() { return "" + (char)tag;}
}
