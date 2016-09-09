
public class Bid implements Comparable<Bid>{
	
	String keyword;
	float price;	
	float cpc;			//cost-per-click
	
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