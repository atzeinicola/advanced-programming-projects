package esercizio2;

public class Identifier extends Token{
	public final String lexeme;
	public Identifier(String lexeme) { super(Token.ID); this.lexeme= new String(lexeme);}
	public String toString() { return "ide: " + lexeme;}
}
