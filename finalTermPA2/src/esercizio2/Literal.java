package esercizio2;

public class Literal extends Token{
	public final String value;
	public Literal(String value) { super(Token.LITERAL); this.value= new String(value);}
	public String toString() { return "lit: " + value;}
}
