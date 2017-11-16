package Ex0;
/**
 * class of WiFi network
 * @author Alexey Titov &   Shalom Weinberger
 * @version 8.0
 */
import java.util.*;
import java.text.*;
public class Time {
	//variables
	private SimpleDateFormat ft;							//format time
	private Date parsingDate;
	//constructor
	public Time()
	{
		this.ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		this.parsingDate=new Date();
	}
	/*
	 * getters and setters for Time
	 */
	public String getFt() {								//returns the time as string
		String str=this.ft.format(this.parsingDate);
		return str;
	}
	public boolean setFt(String str) {					//returns true-if str is time, false-if str is not time
	      try {
	         this.parsingDate = this.ft.parse(str);
	         return true;
	      }catch (ParseException e) { 
	         System.out.println("Err,data is not correct");
	         return false;
	      }
	}
	public Date getFtD()								//returns the time as date
	{
		return this.parsingDate;
	}
}
