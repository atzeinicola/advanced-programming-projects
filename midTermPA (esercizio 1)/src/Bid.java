
public class Bid {
	
	String keyword;
	float price;
	
	Bid (float price, String keyword) {
		this.keyword 	= keyword.toLowerCase();
		this.price 		= price;
	}
}
