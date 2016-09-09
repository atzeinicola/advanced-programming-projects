package main;

import java.io.*;
//import java.util.ArrayList;

import esercizio1.*;
import esercizio2.*;
import esercizio3.*;
//import esercizio4.*;

public class Main {
	public static void main(String []args) throws IOException {
		if (args.length!=1) { System.out.println("Error: Specify input file!");	System.exit(0);	}
		
		Environment env = new Environment();
		Lexer lex = new Lexer(args[0]);
		Parser parser = new Parser(lex, env);
		parser.start() ;
		
		for (Variable v : env.variables) {
			System.out.println(v + " ");
			v.initialize();
		}
		for (Relation c : env.relations) {
			System.out.println(c + " ");
		}
		
		Solver sol = new Solver(env.relations);
		sol.solve(env.variables);
		
		if ( sol.solution!=null ) {
			System.out.println("Solution:");
			for (Variable v : env.variables) {
				if ( v.isNotInitialize()) v.initialize();
				System.out.print(v.currentValue + " ");
			}
			System.out.println();
		}
		else {
			System.out.println("Solution: none");
		}		
		
		
		/*ArrayList<Integer> a = new ArrayList<Integer>();; a.add(1); a.add(2); a.add(3);
		ArrayList<Integer> b = new ArrayList<Integer>();; b.add(4); b.add(5); b.add(6);
		ArrayList<Integer> c = new ArrayList<Integer>();; c.add(7); c.add(8); c.add(9);
		ArrayList<ArrayList<Integer>> sol = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Integer>> ta = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tb = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tc = new ArrayList<ArrayList<Integer>>();
		
		for (int i: a){
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.add(i);
			ta.add(tmp);		
		}
		for (int i: b){
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.add(i);
			tb.add(tmp);		
		}
		for (int i: c){
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.add(i);
			tc.add(tmp);		
		}		
		sol = product( ta, product(tb,tc));
		
		for (ArrayList<Integer> i: sol) {
			System.out.println(i);
		}
		*/
		
		/*for(int f: new Generator()){
			System.out.println("next Fibonacci number is " + f);
			if (f>20) break;
		}*/
		
		System.out.println("OK\n");
	}
	
	/*private static final ArrayList<ArrayList<Integer>> product (ArrayList<ArrayList<Integer>> list1, ArrayList<ArrayList<Integer>> list2){
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		
		for (ArrayList<Integer> l1: list1){			
			for (ArrayList<Integer> l2: list2){
				ArrayList<Integer> tmp = new ArrayList<Integer>(l1);
				tmp.addAll(l2);
				ret.add(tmp);
			}			
		}
		return ret;
	}*/
}
