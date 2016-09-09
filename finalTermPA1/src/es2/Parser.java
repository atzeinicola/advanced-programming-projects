package es2;
import java.io.*;
public class Parser {
	protected Lexer lex;
	protected Token lookahead;
	
	public Parser(File input) throws IOException { this.lex = new Lexer(input); move(); }	
	protected void move() throws IOException { this.lookahead = lex.scan(); }	
	protected void error(String s) { throw new Error("near line "+lex.line+": "+s); }
	
	protected void match(int t) throws IOException {
		if ( this.lookahead.tag == t ) move();		
		else error("unexpected token "+(char)this.lookahead.tag);
	}
	
	public ParseTree start() throws IOException {				//S
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("S", Token.NTERMINAL);
		switch (lookahead.tag) {
			case Token.GRAPH:									//S -> graph ide { E }
				ptree.children.add( new ParseTree(lookahead));		match(Token.GRAPH);
				ptree.children.add( new ParseTree(lookahead));		match(Token.ID);
				ptree.children.add( new ParseTree(lookahead));		match('{');
				ptree.children.add( nodesAndEdges() );
				ptree.children.add( new ParseTree(lookahead));		match('}');
				break;
			default: error("unexpected token. Expected 'graph'");
		}		
		ptree.children.add( new ParseTree(lookahead));		match(Token.EOF);		
		return ptree;
	}
	
	protected ParseTree nodesAndEdges() throws IOException {		//E
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("E",Token.NTERMINAL);		
		switch (lookahead.tag) {			
			case '}':												//E ::= eps
				break;			
			case Token.ID:											//E ::= ide A E'; E
				ptree.children.add( new ParseTree(lookahead)); 		match(Token.ID);
				ptree.children.add( attributes() );
				ptree.children.add( edges() );
				ptree.children.add( new ParseTree(lookahead)); 		match(';');
				ptree.children.add( nodesAndEdges() );					
				break;			
			default: error("unexpected token. Expected '}' or identifier");
		}		
		return ptree;
	}
	
	protected ParseTree edges() throws IOException {				//E'
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("E'",Token.NTERMINAL);		
		switch (lookahead.tag){
			case ';':												//E' ::= eps
				break;			
			case '-':												//E' ::= -- ide A E'			
				ptree.children.add( new ParseTree(lookahead));		match('-');
				ptree.children.add( new ParseTree(lookahead));		match('-');			
				ptree.children.add( new ParseTree(lookahead));		match(Token.ID);
				ptree.children.add( attributes() );
				ptree.children.add( edges() );					
				break;			
			default: error("unexpected token. Expected ';' or '-'");
		}		
		return ptree;
	}
	
	protected ParseTree attributes() throws IOException {			//A
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("A",Token.NTERMINAL);		
		switch (lookahead.tag){
			case ';':												//A ::= eps
			case '-':
			case '}': 
				break;		
			case '[':												//A ::= [ A' A'' ]			
				ptree.children.add( new ParseTree(lookahead));		match('[');
				ptree.children.add( attribute() );
				ptree.children.add( attributesList() );			
				ptree.children.add( new ParseTree(lookahead));		match(']');			
				break;			
			default: error("unexpected token. Expected ';' '-' '}' or '['");
		}		
		return ptree;
	}
	
	protected ParseTree attribute() throws IOException {			//A'
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("A'",Token.NTERMINAL);		
		switch (lookahead.tag){
			case Token.STYLE:										//A' ::= H = B
			case Token.LABEL:			
				ptree.children.add( attributeName() );			
				ptree.children.add( new ParseTree(lookahead));		match('=');
				ptree.children.add( attributeValue() );				
				break;
			default: error("unexpected token. Expected 'style' or 'label'");
		}		
		return ptree;
	}
	
	protected ParseTree attributesList() throws IOException {		//A''
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("A''",Token.NTERMINAL);		
		switch (lookahead.tag){
			case ']':												//A'' ::= eps
				break;		
			case ',':												//A'' ::= , A' A''
				ptree.children.add( new ParseTree(lookahead));		match(',');			
				ptree.children.add( attribute() );
				ptree.children.add( attributesList() );
				break;
			default: error("unexpected token. Expected ']' or ','");
		}		
		return ptree;
	}
	
	protected ParseTree attributeName() throws IOException {		//H
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("H",Token.NTERMINAL);		
		switch (lookahead.tag) {							
			case Token.STYLE:										//H ::= style
				ptree.children.add( new ParseTree(lookahead));		match(Token.STYLE);
				break;		
			case Token.LABEL:										//H ::= label
				ptree.children.add( new ParseTree(lookahead));		match(Token.LABEL);
				break;			
			default: error("unexpected token. Expected 'style' 'label'");
		}		
		return ptree;
	}
	
	protected ParseTree attributeValue() throws IOException {		//B
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("B",Token.NTERMINAL);		
		switch (lookahead.tag) {							
			case Token.LITERAL:										//B ::= "value"			
				ptree.children.add( new ParseTree(lookahead));		match(Token.LITERAL);
				break;		
			case Token.DOTTED:										//B ::= dotted
				ptree.children.add( new ParseTree(lookahead));		match(Token.DOTTED);
				break;			
			default: error("unexpected token. Expected a literal or 'dotted'");
		}
		return ptree;
	}
}
