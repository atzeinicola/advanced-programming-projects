package es2;
import java.io.* ; 
import java.util.*;
public class Lexer {
	public int 		line = 1;
	private char 	peek = ' ';
	private InputStreamReader isr;
	private Hashtable<String,Word> keywords = new Hashtable<String,Word>();
	
	public Lexer (File file) throws FileNotFoundException {
		this.isr = new InputStreamReader(new FileInputStream(file));		
		keywords.put("graph", new Word("graph", Token.GRAPH));
		keywords.put("digraph", new Word("digraph", Token.DIGRAPH));
		keywords.put("label", new Word("label", Token.LABEL));
		keywords.put("style", new Word("style", Token.STYLE));
		keywords.put("dotted", new Word("dotted", Token.DOTTED));
	}
	
	private char readch () throws IOException{ int i = isr.read(); return i==-1? '$': (char)i; }
		
	public Token scan() throws IOException{		
		while (peek==' ' || peek=='\t' || peek == '\n') {
			if (peek == '\n') this.line++;
			peek = this.readch();
		}
		
		if (peek=='$') return new Token( Token.EOF );
		
		if (peek == '"') {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				peek = this.readch();
			} while (peek != '"');
			b.append(peek);
			peek = this.readch();
			String s = b.toString();
			return new Word(s, Token.LITERAL);
		}
		
		if (Character.isLetter(peek)){
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				peek = this.readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = keywords.get(s);
			if (w!=null) return w;
			w = new Word(s,Token.ID);
			return w;	
		}
		
		Token t = new Token (peek);
		peek = ' ';
		return t;
	}
}
