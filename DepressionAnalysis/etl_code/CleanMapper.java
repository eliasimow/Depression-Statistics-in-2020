import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;
import org.apache.hadoop.io.FloatWritable;


public class CleanMapper extends Mapper<LongWritable, Text, Text, Text> {

	 private static final int MISSING = 9999;
	 private static Boolean onlyDepression = true;
	 @Override
	 public void map(LongWritable key, Text value, Context context)
	 throws IOException, InterruptedException {

		 String line = value.toString();
		 
		 /*throw out all lines that do not contain Depressive Disorder
		  */
		 if(onlyDepression && !line.contains("Depressive Disorder")) return;
		 if(!line.contains("By ")) return;
		 
		 line = line.substring(getColumnIndex(2, line));
		 
		 /*this will be the map, ex: By Age	United States 18 - 29 years
		  */
		 
		 String label = "";
		 
		 if(line.contains("By State")) {			 
			 label = line.substring(0,getColumnIndex(2, line)-1);		 
		 }
		 
		 else {
			 label = line.substring(0,getColumnIndex(3, line)-1);		 
		 }
		 
		 
		 /*get value out of 100 float for depression in these 2 weeks
		 */
		 
		 if(line.contains("Non-Hispanic")) {
			 label = line.substring(0,getColumnIndex(4, line)-1);	
			 
		 }
		 String depressionRating = line.substring(getColumnIndex(5,line),getColumnIndex(6,line)-1);
		 String cutOffForDate = line.substring(getColumnIndex(4,line));
		 
		 if(line.contains("Non-Hispanic")) {
			 label = line.substring(0,getColumnIndex(4, line)-1);	
			 depressionRating = line.substring(getColumnIndex(6,line),getColumnIndex(7,line)-1);
			 cutOffForDate = line.substring(getColumnIndex(5,line));
		 }
		 int dashIndex = cutOffForDate.indexOf(" -");
		 int spaceIndex = cutOffForDate.indexOf(" ");
		 float date = monthToDayValue(cutOffForDate);
		 float day = Float.parseFloat(cutOffForDate.substring(spaceIndex+1, dashIndex));
		 
		 if(date == -1 || day == -1) {
			 System.out.println("Date calculation went wrong");
			 return;
		 }
		 day += date;
		
		 

		 String keyValue = String.valueOf(day) + "," + depressionRating;
		 context.write(new Text(label), new Text(keyValue));		 

		 
		 return;
	}
	 	 
	 
	 public static int getColumnIndex(int columnNumber, String line) {
		 int index = line.indexOf(",");
		 int n = columnNumber;
		 while (--n > 0 && index != -1) {
			 index = line.indexOf(",", index + 1);
		 }
	     return index + 1;
	}
	 
	 public static int monthToDayValue(String line) {
		 
		 if(line.indexOf("Apr ") > -1) return 91;
		 if(line.indexOf("May ") > -1) return 121;
		 if(line.indexOf("June ") > -1) return 152;
		 if(line.indexOf("July ") > -1) return 182;
		 if(line.indexOf("Aug ") > -1) return 213;
		 if(line.indexOf("Sep ") > -1) return 244;
		 if(line.indexOf("Oct ") > -1) return 274;
		 if(line.indexOf("Nov ") > -1) return 305;

		 return -1;
	 }
	 	 
}