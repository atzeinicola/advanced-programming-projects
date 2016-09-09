package esercizio2;

import java.io.*;
import java.util.*;

import esercizio1.*;

public class Parser {
	private Lexer lex;
	private Token lookahead;
	private Environment env;
	
	public Parser(Lexer lex, Environment env) throws IOException { this.lex=lex; this.env=env; move(); }
	
	void move() throws IOException { 
		this.lookahead = lex.scan();
	}
	
	void error(String s) { throw new Error("near line "+lex.line+": "+s); }
	
	void match(int t) throws IOException {
		System.out.println(this.lookahead.toString());
		if ( this.lookahead.tag == t ) move();		
		else error("syntax error");
	}
	
	public void start() throws IOException {
		env.variables.addAll( var_list() );		//start -> var_list rel_list
		env.relations.addAll( rel_list() );
		match(Token.EOF);
	}
	
	public Collection<Variable> var_list() throws IOException {
		ArrayList<Variable> tmp = new ArrayList<Variable>();
		
		switch (lookahead.tag) {		//val_list -> var var_list
		case Token.ID:
			tmp.add( var() );
			tmp.addAll(	var_list() );
			break;
		case '{':						//var_list -> eps
		case '!':
		case Token.EOF:
			break;
		default:
			error("syntax error");
		}
		
		return tmp;
	}
	
	public Collection<Relation> rel_list() throws IOException {
		ArrayList<Relation> tmp = new ArrayList<Relation>();
		
		switch (lookahead.tag) {
		case '{':						//rel_list -> rel rel_list
		case '!':
			tmp.add( rel() );
			tmp.addAll( rel_list() );
			break;
		case Token.EOF:					//rel_list -> eps
			break;
		default:
			error("syntax error");			
		}
		
		return tmp;
	}
	
	public Variable var() throws IOException {
		String lexeme = lookahead.getClass()==Identifier.class? ((Identifier)lookahead).lexeme: "";  
		
		match( Token.ID );						//var -> ide = { term term_list }
		match('=');
		match('{');
		
		Variable v = new Variable( lexeme );
		v.domain.add( term() );
		v.domain.addAll( term_list() );
		
		match('}');
		
		return v;		
	}
	
	public Relation rel() throws IOException {
		Relation r = null;

		switch (lookahead.tag) {
		case '{':								//rel -> { pair pair_list }
			r = new Relation(false);
			match('{');
			r.constrains.add( pair() );
			r.constrains.addAll( pair_list() );
			match('}');
			break;
		case '!':								//rel -> !{ pair pair_list }
			match('!');
			r = new Relation(true);
			match('{');
			r.constrains.add( pair() );
			r.constrains.addAll( pair_list() );
			match('}');
			break;
		default:
			error("syntax error");
		}
		
		return r;
	}
	
	public Value term() throws IOException {		
		String tmp = lookahead.getClass()==Identifier.class? ((Identifier)lookahead).lexeme: "";  
		
		match (Token.ID);				//term -> ide
		
		return new Value(tmp);
	}
	
	public Collection<Value> term_list() throws IOException {
		ArrayList<Value> tmp = new ArrayList<Value>();
		
		switch (lookahead.tag) {
		case ',':						//term_list -> , term term_list
			match(',');
			tmp.add( term() );
			tmp.addAll( term_list() );
			break;
		case '}': 						//term_list -> eps
			break;
		default:
			error("syntax error");
		}		
		return tmp;
	}
	
	public Constrain pair() throws IOException {		
		match('(');						//pair -> ( term, term );
		Value v1 = term();
		match(',');
		Value v2 = term();
		match(')');		
		return new Constrain(v1, v2);
	}
	
	public Collection<Constrain> pair_list() throws IOException {
		ArrayList<Constrain> tmp = new ArrayList<Constrain>();
		
		switch (lookahead.tag) {
		case ',':						//pair_list -> , pair pair_list
			match(',');
			tmp.add( pair() );
			tmp.addAll( pair_list() );
			break;
		case '}':						//pair_list -> eps
			break;
		default:
			error("syntax error");

		}
		
		return tmp;
	}
}
