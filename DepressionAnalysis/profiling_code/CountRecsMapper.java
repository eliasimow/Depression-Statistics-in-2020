import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class CountRecsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {


	 @Override
	 public void map(LongWritable key, Text value, Context context)
	 throws IOException, InterruptedException {

		 String line = value.toString();
		 if(line.length()>0) context.write(new Text("entry"), new IntWritable(1));
	}
	 	 
}
