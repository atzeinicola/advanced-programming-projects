package es2;
public class Word extends Token{
	public final String lexeme;	
	public Word(String lexeme, int tag) { super(tag); this.lexeme= new String(lexeme); }
	public String toString() { return lexeme;}
}
