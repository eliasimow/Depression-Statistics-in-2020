	import java.io.IOException;
	import org.apache.hadoop.io.IntWritable;
	import org.apache.hadoop.io.LongWritable;
	import org.apache.hadoop.io.Text;
	import org.apache.hadoop.mapreduce.Mapper;
	import org.apache.hadoop.mapreduce.lib.join.TupleWritable;
	import org.apache.hadoop.io.FloatWritable;


public class DepressionStatsMapper extends Mapper<LongWritable, Text, Text, Text> {

		 private static final int MISSING = 9999;
		 private static Boolean onlyDepression = true;
		 @Override
		 public void map(LongWritable key, Text value, Context context)
		 throws IOException, InterruptedException {

			 String line = value.toString();
			 int position = getValueIndex(line);
			 String category = line.substring(0,position);
			 String result = line.substring(position);
			 
			 /*Specific write:
			  * 
			  */
			 context.write(new Text(category), new Text(result));		 
			 
			 /*General write:
			  * */
			 context.write(new Text("All Categories"), new Text(result));
			 
			 return;
		}
		 
		 public static int getValueIndex(String line) {
			 
			 int lastCommaIndex = line.lastIndexOf(',');
			 while(line.charAt(lastCommaIndex) != ' ' && line.charAt(lastCommaIndex) != '\t' && lastCommaIndex >=0) {
				 lastCommaIndex--;
			 }			 
			 return lastCommaIndex+1;	 
		 }
		 
		 
		 	 		 
}