package Ex0;
/**
 * main for question 2
 * @author Alexey Titov &   Shalom Weinberger
 * @version 8.0
 */
//libraries
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
//class
public class TestCSV
{
	/**
	 * the function read csv files
	 * @param csvFiles list of names cvs files
	 * @return list of data for kml file
	 */
	public static ArrayList<DataWIFI> ReadCSV(ArrayList<String> csvFiles)
	{
		//variables
		ArrayList<DataWIFI> dwf=new ArrayList<DataWIFI>();			//data of WiFiNetwork
		String IDphone="";											//id of cellular phone
		CsvReader row =null;
		//read file csv and filtering data
		for (int i=0;i<csvFiles.size();i++)
		{
			try {
				row=new CsvReader(new FileReader(csvFiles.get(i)));
				//save ID of phone in to IDphone
				if (row.readRecord())
				{
					if (row.getColumnCount()==11)  //if were is less or more then 8 it's not our format
					{
						try {
							IDphone=row.get(5).substring(8); 
						}catch(StringIndexOutOfBoundsException e) {
							JOptionPane.showMessageDialog(null, "File is not correct");
							row.close();
							continue;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "File is not correct");
						row.close();
						continue;
					}
				}
				//read line with names of columns
				if (!row.readRecord() || row.getColumnCount()!=11)
				{
					JOptionPane.showMessageDialog(null, "File is not correct");
					row.close();
       		 		continue;
       	 		}	
				//read data from columns
				while (row.readRecord())
				{
					if (row.getColumnCount()==11 && row.get(10).equals("WIFI")) //check that 11 columns are in the line and type is wi-fi network
					{
						DataWIFI tmpWIFI=new DataWIFI();
						WIFI wf=new WIFI();
						int cnt=0;					//variables for check MAC,Signal
						int cnt2=0;					//variables for check latitude,longitude
						boolean flagtime;			//flag for check time
						tmpWIFI.setID(IDphone);
						//location
						Location place=new Location();
						try{
							place.setAlt(Double.parseDouble(row.get(8)));
							cnt2+=place.setLat(Double.parseDouble(row.get(6)));
							cnt2+=place.setLon(Double.parseDouble(row.get(7)));
						}catch(NumberFormatException e){
							continue;
						}
						//WIFI data
						try {
							cnt+=wf.setFrequency(Integer.parseInt(row.get(4)));
							cnt+=wf.setMAC(row.get(0));
							wf.setSSID(row.get(1));
							cnt+=wf.setSignal(Integer.parseInt(row.get(5)));
						}catch(NumberFormatException e){
							continue;
						}	
						tmpWIFI.setWiFi(wf);
						tmpWIFI.setLla(place);
						flagtime=tmpWIFI.setTIME(row.get(3));
						tmpWIFI.setWIFINetwork(1);
						//check if MAC,Signal,time and location are correct
						if (cnt!=3 || cnt2!=2 || !flagtime)   
							continue;
						//first wifi
						if (dwf.size()==0)
							dwf.add(tmpWIFI);
						else
						{
							int j;
							for (j=0;j<dwf.size();j++)								//time and location are equals
								if ((dwf.get(j).getTIMED().compareTo(tmpWIFI.getTIMED())==0)
									&& (dwf.get(j).getLla().compareLLA(tmpWIFI.getLla())==0))
								{
									dwf.get(j).setWiFi(wf);
									break;
								}
							//time or location are not equals
							if (j==dwf.size())
								dwf.add(tmpWIFI);
						}
					}
				}
				row.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dwf;
	}
	/**
	 * the function write csv file
	 * @param dwf list of data for csv file
	 */
	public static void WriteCSV(ArrayList<DataWIFI> dwf)
	{
		//variable
		String OUTcsvFile="";									//output csv file
		//select the location of the file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.CSV","*.*");
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
			try {
				//user wrote at the end csv
				if (fc.getSelectedFile().getAbsolutePath().substring(fc.getSelectedFile().getAbsolutePath().length()-4).equals(".csv"))
					OUTcsvFile=fc.getSelectedFile().getAbsolutePath();
				else 	//user did not wrote at the end csv
					OUTcsvFile=fc.getSelectedFile().getAbsolutePath()+".csv";
			}catch (Exception e ) {
				JOptionPane.showMessageDialog(null, "an error occurred, the file was not saved.\ngoodbye");
				return;
			}
		}
		else	//user did not select a file to save
		{
			JOptionPane.showMessageDialog(null, "you did not select a file to save,\ngoodbye");
		    return;
	    }	
		try {
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter(OUTcsvFile, false), ',');	
     		//headers for first row
			csvOutput.write("TIME");		csvOutput.write("ID");
			csvOutput.write("latitude");	csvOutput.write("longitude");	csvOutput.write("altitude");
			csvOutput.write("#WiFi networks");
			for (int i=1;i<11;i++)
			{
				csvOutput.write("SSID"+i);
				csvOutput.write("MAC"+i);
				csvOutput.write("Frequency"+i);
				csvOutput.write("Signal"+i);
			}
			csvOutput.endRecord();
			//write out a few rows
			for(int i=0;i<dwf.size();i++)							//write rows of data wifi
			{
				String[] tmp=dwf.get(i).toString().split(",");
				csvOutput.writeRecord(tmp);
			}
			csvOutput.close();
			JOptionPane.showMessageDialog(null, "csv record saved");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "csv record not saved");
		}catch (Exception e) {
    	JOptionPane.showMessageDialog(null, "csv record not saved");
		}
	}
	//main
	public static void main(String[] args) 
	{
		//variables
		ArrayList<String> files=new ArrayList<String>();		//list csv files
		ArrayList<DataWIFI> DWF=new ArrayList<DataWIFI>();		//data of WiFiNetwork
		int ret;												//flag for check a directory is selection
		//File chooser
		JFileChooser fileopen = new JFileChooser();
		//only directory
		fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileopen.setAcceptAllFileFilterUsed(false);
		//dialog box for determining the desired directory
		ret=fileopen.showDialog(null, "Open directory");
		//check if directory is not selected
		if (!(ret == JFileChooser.APPROVE_OPTION))
		{
        	JOptionPane.showMessageDialog(null, "Directory is not selected\ngoodbye");
        	System.exit(2);
        }	
		File []fList;        
		File F = new File(fileopen.getSelectedFile().getAbsolutePath());  //the path to the directory
		//all files that are in the folder     
		fList = F.listFiles();
		//runs at the folder.
		for(int i=0; i<fList.length; i++)           
		{
			String mark=fList[i].getName();  
			//check if name is csv file
		    if(fList[i].isFile() && mark.substring(mark.length()-3).equals("csv"))
		    	files.add(fileopen.getSelectedFile().getAbsolutePath()+"\\"+fList[i].getName());
		}
		//no files
		if (files.isEmpty())
		{
        	JOptionPane.showMessageDialog(null, "in the folder there are no csv files");
        	System.exit(2);
        }
		else
		DWF=ReadCSV(files);
		WriteCSV(DWF);
	}
}
