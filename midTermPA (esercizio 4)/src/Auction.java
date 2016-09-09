import java.util.*;

public class Auction {
	
	HashMap<String,HashMap<Campaign,Float>> associationQueryWinnerCampaigns = new HashMap<String,HashMap<Campaign,Float>>();		//associate each query with the campaigns that win the auction and the amout that will be charged if click
	
	public void associate (StreamOfQueries stream, List<Campaign> campaigns, int k) {
		
		associationQueryWinnerCampaigns.clear();
		
		Iterator<String> itString;
		Iterator<Campaign> itCampaign;
		Iterator<Bid> itBid;
		
		String currentQuery;
		Campaign currentCampaign;
		Bid currentBid;		
		
		List<Bid> bidsOfCurrentQuery = new ArrayList<Bid>();		
		//will containthe bids to the current query
		
		itString = stream.queries.iterator();
		while(itString.hasNext()) {						//iter on queries
			currentQuery = itString.next();				//select the current query
			bidsOfCurrentQuery.clear();					//clear the data structure
			itCampaign = campaigns.iterator();
			associationQueryWinnerCampaigns.put(currentQuery, new HashMap<Campaign,Float>());
			
			while(itCampaign.hasNext()){					//iter on campaigns: for each query we search if there are some bids
				currentCampaign = itCampaign.next();		//select the current campaign
				itBid = currentCampaign.bids.iterator();
				
				while(itBid.hasNext()){							//iter on bids of the current campaign
					currentBid = itBid.next();					//select the current bid
					
					if (										//if query contains the keyword
							currentQuery.contains( currentBid.keyword + " " ) ||
							currentQuery.contains( " " + currentBid.keyword) ||
							currentQuery.contains( " " + currentBid.keyword + " " )
							){
						bidsOfCurrentQuery.add(currentBid);			//add the current bid
					}
					else {
						if (BidExt.class.isInstance(currentBid)){		//if the current bid is the subclass BidExt
							String[] otherKeywords = ((BidExt) currentBid).otherKeywords;
							if (otherKeywords!=null)
								for (int i=0; i<otherKeywords.length; i++) {
									if (	!otherKeywords[i].equals(currentBid.keyword)	&&
											(
											currentQuery.contains( otherKeywords[i] + " " ) ||
											currentQuery.contains( " " + otherKeywords[i]) ||
											currentQuery.contains( " " + otherKeywords[i] + " " )
											)
										){
										bidsOfCurrentQuery.add(currentBid);	//add the current bid
										i = otherKeywords.length;			//end the for cycle
									}
								}
						}
					}

				}
			}
			
			Collections.sort(bidsOfCurrentQuery);		//order bids by price

			//START THE AUCTION - associate to each bid the curresponding cost per click
			float auctionPrice = 0.0F ;
			float oldPrice = 0.0F;			

			if (true) System.out.println("Q: "+currentQuery);
			
			for (int i=0; i<bidsOfCurrentQuery.size(); i++){	//iter on bids from lower to highest
				currentBid = bidsOfCurrentQuery.get(i);			//select the first
				
				if 	(currentBid.price>oldPrice)					//refresh auctionPrice if currentBid il greater that previous bid
					auctionPrice = oldPrice;					//bids with same price will be charged the same amount
				
				currentBid.cpc = auctionPrice+0.01F;
				oldPrice = currentBid.price;					//refresh oldPrice and remove the currentBid
				

				if (true) { 
					System.out.print( currentBid.keyword + "\t- " + currentBid.price + " " + currentBid.ctr + " " + currentBid.cpc + " " + currentBid.ctr*currentBid.cpc);
					if (BidExt.class.isInstance(currentBid)){		//if the current bid is the subclass BidExt
						String[] otherKeywords = ((BidExt) currentBid).otherKeywords;
						if (otherKeywords!=null)
							for (int j=0; j<otherKeywords.length; j++) {
								System.out.print( "  " + otherKeywords[j]);
							}
					}
					System.out.println();
				}
			}
			
			Collections.sort(bidsOfCurrentQuery, new OrderByCtrTimesCpc());		//order bids by ctr*cpc									
			
			//exctract the k best bids
			if (bidsOfCurrentQuery.size()>k)
				bidsOfCurrentQuery = bidsOfCurrentQuery.subList(bidsOfCurrentQuery.size()-k, bidsOfCurrentQuery.size());
			
			//add the remaining bids to the data structure associationQueryWinnerCampaigns
			for (int i=0; i<bidsOfCurrentQuery.size(); i++){					
				currentBid = bidsOfCurrentQuery.get(i);			//select the first				
				itCampaign = campaigns.iterator();	
					
				while(itCampaign.hasNext()){					//iter on campaigns to find which 
																//campaign place the current bid
					currentCampaign = itCampaign.next();
					if (currentCampaign.bids.contains( currentBid )) {
						//put the correct info on the data structure
						associationQueryWinnerCampaigns.get(currentQuery).put(currentCampaign, currentBid.cpc );
						if (true) { 
							System.out.print(currentCampaign.advertiser.advertisement + ":\t" + currentBid.keyword + "\t- " + currentBid.ctr*currentBid.cpc);
							if (BidExt.class.isInstance(currentBid)){		//if the current bid is the subclass BidExt
								String[] otherKeywords = ((BidExt) currentBid).otherKeywords;
								if (otherKeywords!=null)
									for (int j=0; j<otherKeywords.length; j++) {
										System.out.print( "  " + otherKeywords[j]);
									}
							}
							System.out.println();
						}
					}
				}
			}
			
			for (int i=0; i<bidsOfCurrentQuery.size(); i++){	//iter on bids from lower to highest
				currentBid = bidsOfCurrentQuery.get(i);			//select the first
								
				if (i >= bidsOfCurrentQuery.size()-k) {			//when remain k bids
					itCampaign = campaigns.iterator();	
					
					while(itCampaign.hasNext()){					//iter on campaigns to find which campaign place the bid
						currentCampaign = itCampaign.next();
						if (currentCampaign.bids.contains( currentBid )) {
							associationQueryWinnerCampaigns.get(currentQuery).put(currentCampaign, currentBid.cpc  );
							
							
						}
					}
				}
				
			}			
		}		
	}	
}