import java.util.*;


public class Main {

	public static void main(String[] args) {
		
		Campaign cmp0 = new Campaign( new Advertiser("Advertisement of 0"),100.0F);
		Campaign cmp1 = new Campaign( new Advertiser("Advertisement of 1"),100.0F);
		Campaign cmp2 = new Campaign( new Advertiser("Advertisement of 2"),100.0F);
		Campaign cmp3 = new Campaign( new Advertiser("Advertisement of 3"),100.0F);
		
		cmp0.bids.add( new Bid( 1.0F, "pippo") );
		cmp0.bids.add( new Bid( 1.5F, "pluto") );
		cmp0.bids.add( new Bid( 2.0F, "alice") );
		cmp0.bids.add( new Bid( 2.5F, "bob"  ) );
		
		cmp1.bids.add( new Bid( 2.5F, "pippo") );
		cmp1.bids.add( new Bid( 1.0F, "pluto") );
		cmp1.bids.add( new Bid( 1.5F, "alice") );
		cmp1.bids.add( new Bid( 2.0F, "bob"  ) );
		
		cmp2.bids.add( new Bid( 2.0F, "pippo") );
		cmp2.bids.add( new Bid( 2.5F, "pluto") );
		cmp2.bids.add( new Bid( 1.0F, "alice") );
		cmp2.bids.add( new Bid( 1.5F, "bob"  ) );
		
		cmp3.bids.add( new Bid( 1.5F, "pippo") );
		cmp3.bids.add( new Bid( 2.0F, "pluto") );
		cmp3.bids.add( new Bid( 2.5F, "alice") );
		cmp3.bids.add( new Bid( 1.0F, "bob"  ) );
		
		StreamOfQueries soq = new StreamOfQueries();
		//soq.queries.add("Pippo è bello");
		//soq.queries.add("Pluto è bello");
		//soq.queries.add("Alice è bello");
		//soq.queries.add("Bob è bello");
		soq.queries.add("Alice e Bob sono due bambini");
		//soq.queries.add("Pluto e Bob sono nomi da cane");
		//soq.queries.add("Alice è una donna");
		soq.queries.add("Mario è un uomo");
		
		List<Campaign> campaigns = new ArrayList<Campaign>();
		campaigns.add(cmp0);
		campaigns.add(cmp1);
		campaigns.add(cmp2);
		campaigns.add(cmp3);
		
	}

}
