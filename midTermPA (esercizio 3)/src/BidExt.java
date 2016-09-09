import java.util.*;

public class BidExt extends Bid{

	String[] otherKeywords;
		
	BidExt (float price, String... keywords ) {
		super(price, keywords[0]);
		
		for(int i=0; i<keywords.length; i++)
			keywords[i].toLowerCase();
		
		if (keywords.length>1)
			otherKeywords = Arrays.copyOfRange( keywords, 1, keywords.length);
	}
}
