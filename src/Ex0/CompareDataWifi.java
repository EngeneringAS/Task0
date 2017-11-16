package Ex0;
//library
import java.util.Comparator;
/**
 * interface for question 3
 * @author Alexey Titov &   Shalom Weinberger
 * @version 8.0
 */
public interface CompareDataWifi extends Comparator<DataWIFI> {
	int compare(DataWIFI arg1,DataWIFI arg2);						//for sorting 
	boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3);		//for filtering
}