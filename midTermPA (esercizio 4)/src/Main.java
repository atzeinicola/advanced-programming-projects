import java.util.*;


public class Main {

	public static void main(String[] args) {
		
		//we create the campaigns
		Campaign cmp0 = new Campaign( new Advertiser("Advertisement of 0"),100.0F, 0.05F);
		Campaign cmp1 = new Campaign( new Advertiser("Advertisement of 1"),100.0F, 0.03F);
		Campaign cmp2 = new Campaign( new Advertiser("Advertisement of 2"),100.0F, 0.07F);
		Campaign cmp3 = new Campaign( new Advertiser("Advertisement of 3"),100.0F, 0.03F);
				
		cmp0.bids.add( new BidExt( 1.0F, "pluto", "pippo") );
		cmp0.bids.add( new BidExt( 2.0F, "alice", "bob") );
		cmp0.setCtrOnBids();
		
		
		cmp1.bids.add( new BidExt( 2.5F, "pippo", "bob") );
		cmp1.bids.add( new BidExt( 1.0F, "pluto", "alice") );
		cmp1.setCtrOnBids();
		
		cmp2.bids.add( new Bid( 2.0F, "pippo") );
		cmp2.bids.add( new BidExt( 2.5F, "pluto") );
		cmp2.bids.add( new BidExt( 1.0F, "alice","bob") );
		cmp2.setCtrOnBids();
		
		cmp3.bids.add( new BidExt( 1.5F, "pippo") );
		cmp3.bids.add( new BidExt( 2.0F, "pluto") );
		cmp3.bids.add( new Bid( 2.5F, "alice") );
		cmp3.bids.add( new Bid( 1.0F, "bob"  ) );
		cmp3.setCtrOnBids();
		
		//we create the stream of query
		StreamOfQueries soq = new StreamOfQueries();
		//soq.addQuery("Pippo è bello");
		//soq.addQuery("Pluto è bello");
		//soq.addQuery("Alice è bello");
		//soq.addQuery("Bob è bello");
		soq.addQuery("Alice e Bob sono due bambini");
		//soq.addQuery("Pluto e Bob sono nomi da cane");
		//soq.addQuery("Alice è una donna");
		//soq.addQuery("Mario è un uomo");
		
		//we aggregate the campaigns in a list
		List<Campaign> campaigns = new ArrayList<Campaign>();
		campaigns.add(cmp0);
		campaigns.add(cmp1);
		campaigns.add(cmp2);
		campaigns.add(cmp3);
		
		//create the auction
		Auction auct = new Auction();
		int k = 2;
		auct.associate(soq, campaigns, k);		
		
		Iterator<String> itString;
		Iterator<Campaign> itCampaign;
		
		itString = soq.queries.iterator();
		while (itString.hasNext()){				//iter on query
			String currentQuery = itString.next();
			System.out.print( currentQuery + ": " );
			
			HashMap<Campaign,Float> tmp = auct.associationQueryWinnerCampaigns.get( currentQuery );
			
			itCampaign = tmp.keySet().iterator();
						
			while (itCampaign.hasNext()){			//iter on campaigns that won the auction of the current query
				Campaign currentCampaign = itCampaign.next();
				
				System.out.print( currentCampaign.advertiser.advertisement + "(" + tmp.get(currentCampaign) + ")  ");
			}
			System.out.println();
		}
		
	}

}
