package esercizio1;

public class Constrain {
	public Value first;
	public Value second;
	
	public Constrain (Value a, Value b) {first=a; second=b;}
	
	public Boolean check( Variable v1, Variable v2) {
		if ( !v1.domain.contains(first) ) return true;
		
		return v1.currentValue==first && v2.currentValue==second;
	}
	
	public String toString() { return "(" + first.toString() + "," + second.toString() + ")"; }
}