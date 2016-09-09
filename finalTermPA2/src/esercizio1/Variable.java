package esercizio1;

import java.util.ArrayList;

public class Variable {
	
	public String name;
	public ArrayList<Value> domain = new ArrayList<Value>();
	public Value currentValue;
	
	public Variable (String s) {name=s;}
	
	public String toString() {
		String s = name + " = { ";
		for (Value v: domain) s+= v.toString() + " ";
		return s += " }";
	}
	
	public void initialize() { currentValue = domain.get(0); }
	public Boolean isNotInitialize() { return currentValue==null; }
	public Boolean next() {
		int i = domain.indexOf(currentValue)+1;
		if (i==domain.size()) return false;
		
		currentValue = domain.get(i);
		return true;
	}
	
	
}
