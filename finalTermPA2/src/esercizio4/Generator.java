package esercizio4;

import java.util.*;
//import esercizio2.*;

public class Generator implements Iterable<Integer>{

	@Override
	public Iterator<Integer> iterator() {
		return new Iter();
	}
	
}

class Iter implements Iterator<Integer>{
	int counter=0;
	
	@Override
	public boolean hasNext() {
		return counter<10;
	}

	@Override
	public Integer next() {
		return counter ++;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}