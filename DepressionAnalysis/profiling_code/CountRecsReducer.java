import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NeighborhoodReduce extends Reducer<Text, IntWritable, Text, IntWritable>{

	@Override
	 public void reduce(Text key, Iterable<IntWritable> values, Context context)
	 throws IOException, InterruptedException {

	 int entryCount = 0;
	 
	 for (IntWritable value : values) {
		 repeatCount++;
	 }
	 context.write(new Text("Number of Records:"), new IntWritable(repeatCount));
	 }
}