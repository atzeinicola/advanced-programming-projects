package esercizio2;

public class Token {	
	public final int tag;
	public final static int ID=256, NUM=257, LITERAL=258, EOF='$';	
	public Token (int tag) {this.tag = tag;}
	public String toString() { return "" + (char)tag;}
}
