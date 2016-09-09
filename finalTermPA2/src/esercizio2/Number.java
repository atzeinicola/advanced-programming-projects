package esercizio2;

public class Number extends Token {
	public final int value;
	public Number(int value) { super(Token.NUM); this.value=value;}
	public String toString() { return "num: " + value;}
}
