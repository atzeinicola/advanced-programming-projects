package esercizio3;

import java.util.*;
import esercizio1.*;

public class Solver {

	private Collection<Relation> relations;
	public Collection<Variable> solution;
	
	public Solver (Collection<Relation> r) { relations=r; }
	
	public Boolean solve ( Collection<Variable> vars ) {
		//Given in input a collection of variables,
		// the method initialize the variables to satisfy all relations
		
		for (Variable v : vars) {
			if ( v.isNotInitialize()) v.initialize();
			System.out.print(v.currentValue + " ");
		}
		System.out.println();		
		
		
		if ( checkAllRelations( vars ) ) {
			solution = new ArrayList<Variable>(vars);
			return true;
		}
		else {
			Iterator<Variable> it = vars.iterator();
			while ( it.hasNext()) {
				Variable v = it.next();
				if ( v.next() ) {
					break;
				}
				else {
					if (it.hasNext())
						v.initialize();
					else 
						return false;
				}
			}		
			
			return solve (vars);
		}
	}
	
	private Boolean checkAllRelations (Collection<Variable> vars) {
		if (relations.size()==0) {
			solution = new ArrayList<Variable>(vars);
			return true;		
		}
		
		Boolean flag = true;
		for (Relation r : relations) {
			flag = flag && r.check(vars);
		}
		
		
		return flag;
	}
}
