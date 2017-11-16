package Ex0;
/**
 * class of WiFi network
 * @author Alexey Titov &   Shalom Weinberger
 * @version 8.0
 */
public class WIFI 
{
	//variables
	private String SSID;		//Service Set Identification
	private String MAC;			//Media Access Control address
	private int Frequency;		//Channel of WiFi
	private int Signal;			//WiFi signal power
	/**
	 * constructor for WIFI
	 */
	public WIFI()
	{
		super();
		SSID = "N\\A";
		MAC = "N\\A";
		Frequency = 0;
		Signal = 0;
	}
	/*
	 * getters and setters for SSID
	 */
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	/*
	 * getters and setters for MAC
	 */
	public String getMAC() {
		return MAC;
	}
	/**
	 * checks that the letters or numbers
	 * @param ch1-first character
	 * @param ch2-second character
	 * @return true-if characters are numbers or letters (a,b,c,d,e,f), false-otherwise
	 */
	public boolean CheckNumChar(char ch1,char ch2)
	{
		if ((ch1<'a' || ch1>'f') && (ch1<'0' || ch1>'9'))
			return true;
		if ((ch2<'a' || ch2>'f') && (ch2<'0' || ch2>'9'))
			return true;
		return false;
	}
	/**
	 * 
	 * @param mAC-Media Access Control address
	 * @return 1-if mAC is correct, 0-if mAC is not correct
	 */
	public int setMAC(String mAC) {			
		if (mAC.length()!=17)
			return 0;
		String[] tmp = mAC.split(":");
		if (tmp.length!=6)
			return 0;
		for (int i=0;i<tmp.length;i++)
		{
			if (tmp[i].length()!=2 || CheckNumChar(tmp[i].charAt(0),tmp[i].charAt(1)))
				return 0;
		}
		this.MAC = mAC;
		return 1;	
	}
	/*
	 * getters and setters for Frequency
	 */
	public int getFrequency() {
		return this.Frequency;
	}
	public int setFrequency(int frequency) {			//return 1-if frequency is correct, and 0-if frequency is not correct
		if (frequency>0)
		{
			this.Frequency = frequency;
			return 1;
		}
		return 0;
	}
	/*
	 * getters and setters for Signal
	 */
	public int getSignal() {
		return Signal;
	}
	public int setSignal(int signal) {				//return 1-if signal is correct, and 0-if signal is not correct
		if (signal<0)
		{
			Signal = signal;
			return 1;
		}
		return 0;
	}
	/**
	 * toString for we program
	 */
	@Override
	public String toString() 
	{
		return this.SSID + "," + this.MAC + "," + this.Frequency + "," + this.Signal;
	}
}
