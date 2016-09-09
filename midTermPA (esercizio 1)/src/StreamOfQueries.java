import java.util.*;


public class StreamOfQueries {
	
	List<String> queries = new ArrayList<String>();
	
	public void addQuery (String q) {
		this.queries.add(q.toLowerCase());
	}
}
