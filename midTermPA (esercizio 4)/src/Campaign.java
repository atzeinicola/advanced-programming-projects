import java.util.*;

public class Campaign {
	
	List<Bid> bids 	= new ArrayList<Bid>();
	Advertiser		advertiser;
	float 			totalAmount;
	
	float ctr;		//click-through rate: rate between clicks and views
	
	Campaign (Advertiser advertiser, float totalAmount, float ctr) { 
		this.advertiser = advertiser;
		this.totalAmount = totalAmount;
		this.ctr = ctr;
	}
	
	public void setCtrOnBids () {
		Iterator<Bid> it = bids.iterator();
		while(it.hasNext()){
			it.next().ctr = this.ctr;
		}
	}
}
