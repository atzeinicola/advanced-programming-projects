package es1;
import java.util.*;
public class Diagram {
	public String name;
	public boolean directed = false;	
	public HashMap<String,Node> nodes = new HashMap<String,Node>();
	public HashMap<String,Edge> edges = new HashMap<String,Edge>();
	public Diagram(String s) { this.name=s; }	
	public String toString() { return name + "\n\t" + nodes + "\n\t" + edges; }
}
