package Ex0;
/**
 * class of location
 * @author Alexey Titov &   Shalom Weinberger
 * @version 8.0
 */
public class Location 
{
	//variables
	private double lat;					//latitude
	private double lon;					//longitude
	private double alt;					//altitude
	/**
	 * constructor for Location
	 * @param lat-latitude
	 * @param lon-longitude
	 * @param alt-sea level
	 */
	public Location(double lat, double lon, double alt)
	{
		super();
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
	}
	public Location()
	{
		super();
		this.lat = 0;
		this.lon = 0;
		this.alt = 0;
	}
	/**
	 * getters and setters for lat
	 */
	public double getLat() {
		return lat;
	}
	public int setLat(double lat) {			//return 0-if latitude is correct, 0-if latitude is not correct
		if (lat<-90 || lat>90)
			return 0;
		this.lat = lat;
		return 1;
	}
	/**
	 * getters and setters for lon
	 */
	public double getLon() {
		return lon;
	}
	public int setLon(double lon) {			//return 0-if longitude is correct, 0-if longitude is not correct
		if (lon<-180 || lon>180)
			return 0;
		this.lon = lon;
		return 1;
	}
	/**
	 * getters and setters for alt
	 */
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	/**
	 * the function compare coordinates
	 * @param lla2 coordinates for compare
	 * @return 0-equals, -1- lla2 is more, 1- other
	 */
	public int compareLLA(Location lla2)
	{
		if (this.lat-lla2.getLat()==0)
		{
			if (this.lon-lla2.getLon()==0)
			{
				if (this.alt-lla2.getAlt()==0)
					return 0;
				else
					if (this.alt-lla2.getAlt()>0)
						return 1;
					return -1;
			}
			else
			{
				if (this.lon-lla2.getLon()>0)
					return 1;
				return -1;
			}
		}
		if (this.lat-lla2.getLat()>0)
			return 1;
		return -1;
	}
	/**
	 * toString for we program
	 */
	@Override
	public String toString() {
		return lat + "," + lon + "," + alt+",";
	}	
}
