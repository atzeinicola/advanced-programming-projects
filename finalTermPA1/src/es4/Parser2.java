package es4;
import java.io.*;
import es2.*;
public class Parser2 extends Parser{

	public Parser2(File input) throws IOException {	super(input); }

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
			case Token.DIGRAPH:									//S -> graph ide { D }			
				ptree.children.add( new ParseTree(lookahead));		match(Token.DIGRAPH);
				ptree.children.add( new ParseTree(lookahead));		match(Token.ID);
				ptree.children.add( new ParseTree(lookahead));		match('{');
				ptree.children.add( nodesAndDirectedEdges() );			
				ptree.children.add( new ParseTree(lookahead));		match('}');			
				break;	
			default: error("unexpected token. Expected 'graph' or 'digraph'");
		}		
		ptree.children.add( new ParseTree(lookahead));
		match(Token.EOF);
		return ptree;
	}		
			
	public ParseTree nodesAndDirectedEdges() throws IOException {	//D
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("D",Token.NTERMINAL);			
		switch (lookahead.tag) {			
			case '}':												//D ::= eps
				break;				
			case Token.ID:											//D ::= ide A D'; D							
				ptree.children.add( new ParseTree(lookahead));		match(Token.ID);
				ptree.children.add( attributes() );				
				ptree.children.add( directedEdges() );				
				ptree.children.add( new ParseTree(lookahead));		match(';');
				ptree.children.add( nodesAndDirectedEdges() );	
				break;				
			default: error("unexpected token. Expected '}' or identifier");
		}			
		return ptree;
	}
	
	public ParseTree directedEdges() throws IOException {	//D'
		ParseTree ptree = new ParseTree();
		ptree.node = new Word("D'",Token.NTERMINAL);		
		switch (lookahead.tag){
			case ';':											//D' ::= eps
				break;			
			case '-':											//D' ::= -> ide A D'
				ptree.children.add( new ParseTree(lookahead));		match('-');				
				ptree.children.add( new ParseTree(lookahead));		match('>');				
				ptree.children.add( new ParseTree(lookahead));		match(Token.ID);						
				ptree.children.add( attributes() );
				ptree.children.add( edges() );						
				break;				
			default: error("unexpected token. Expected ';' or '-'");
		}		
		return ptree;
	}
}
