import java.util.*;

public class Campaign {
	
	List<Bid> bids 	= new ArrayList<Bid>();
	Advertiser		advertiser;
	float 			totalAmount;
	
	Campaign (Advertiser advertiser, float totalAmount) { 
		this.advertiser = advertiser;
		this.totalAmount = totalAmount; 
	}

}
