package esercizio2;

import java.io.* ; 

public class Lexer {
	public int 		line = 1;
	private char 	peek = ' ';
	private InputStreamReader isr;
	
	public Lexer (String file) throws FileNotFoundException {this.isr = new InputStreamReader(new FileInputStream(file));}
	
	private char readch () throws IOException{
		int i = isr.read();
		return i==-1? '$': (char)i;
	}
		
	public Token scan() throws IOException{
		
		while (peek==' ' || peek=='\t' || peek == '\n') {
			if (peek == '\n')
				this.line++;
			peek = this.readch();
		}
		
		if (peek=='$')
			return new Token( Token.EOF );
		
		/*if (peek == '"') {
			peek = this.readch();			//skip double quotes
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				peek = this.readch();
			} while (peek != '"');
			peek = this.readch();
			String s = b.toString();
			return new Literal(s);
		}*/
		
		
		
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10*v + Character.digit(peek, 10);
				peek = this.readch();
			} while (Character.isDigit(peek));
			return new Number(v);
		}
		
		if (Character.isLetter(peek)){
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				peek = this.readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			return new Identifier(s);
		}
		
		Token t = new Token (peek);
		peek = ' ';
		return t;
	}
}
