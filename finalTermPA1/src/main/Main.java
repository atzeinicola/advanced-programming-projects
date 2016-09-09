package main;
import java.io.*;
import es1.*; import es2.*; import es3.*; import es4.*; import es5.*;
public class Main {	
	public static void main(String[] args) throws IOException {	
		File file = new File("graph.txt");
		Parser parser = new Parser(file);	//usare Parser2() per i grafi orientati
		ParseTree ptree = parser.start();
		ptree.print();		
		HtmlGenerator html = new HtmlGenerator();	//usare HtmlGenerator2() per muovere i nodi
		html.initializeDiagram(ptree);
		System.out.println( html.getHtml() );		
	}
}
