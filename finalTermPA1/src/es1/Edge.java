package es1;
public class Edge {
	public static final int DEFAULT=0, DOTTED=1;	
	public int style = Edge.DEFAULT;
	public String label;
	public boolean directed = false;	
	public Node from, to;	
	public String toString() {
		return from + (directed?"->":"--") + to + " label:"+label+(style==0?"":" style:dotted");
	}
}
