
public class Advertiser {

	String advertisement;

	Advertiser (String ad) { this.advertisement = ad.length()>95? ad.substring(0, 95): ad; }	
}
