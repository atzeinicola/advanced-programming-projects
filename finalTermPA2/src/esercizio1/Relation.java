package esercizio1;

import java.util.*;

public class Relation {
	
	private Boolean negated;
	public ArrayList<Constrain> constrains = new ArrayList<Constrain>();
	
	public Relation (Boolean n) {this.negated = n;}
		
	
	public Boolean check( Collection<Variable> variables) {
		Variable[] vars = variables.toArray(new Variable[0]);
		for ( int i=0; i<vars.length-1; i++ ) {
			for ( int j=0; j<vars.length; j++ ) {
				Variable v1 = vars[i];
				Variable v2 = vars[j];
				if ( ! checkPair(v1,v2) )	//if a pair fails, the configuration of variables isn't good
					return false;
			}
		}
		return true;
	}
	
	private Boolean checkPair( Variable v1, Variable v2) {
		Boolean ret = negated? false: true;
		for ( Constrain c : constrains ){
			if (! c.check(v1, v2) ) {
				ret = negated? true: false;		//if one constrain fails, the entire relation fails
				break;
			}
		}
		return ret;
	}
	
	public String toString() {
		String s = negated? "!{ ": "{ ";
		for (Constrain c: constrains) 
			s += c.toString() + " ";
		return s += " }";
	}
}

