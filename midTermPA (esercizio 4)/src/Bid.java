import java.util.*;

public class Bid implements Comparable<Bid>{
	
	String keyword;
	float price;
	
	float cpc;						//cost-per-click
	float ctr;						//click-through rate
	
	Bid (float price, String keyword) {
		this.keyword 	= keyword.toLowerCase();
		this.price 		= price;
	}

	@Override
	public int compareTo(Bid b) {
		if ( this.price < b.price )
			return -1;
		else
			return 1;		
	}
}

class OrderByCtrTimesCpc implements Comparator<Bid> {

	@Override
	public int compare(Bid a, Bid b) {
		if ( a.ctr*a.cpc < b.ctr*b.cpc )
			return -1;
		else
			return 1;	
	}
	
}