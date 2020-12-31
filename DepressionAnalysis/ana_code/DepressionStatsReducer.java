import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*; 

public class DepressionStatsReducer extends Reducer<Text, Text, Text, Text>{

	@Override
	 public void reduce(org.w3c.dom.Text key, Iterable<Text> values, Context context)
	 throws IOException, InterruptedException {

		List<Float> dates = new ArrayList<Float>();
		List<Float> depressionValues = new ArrayList<Float>();
		
		int highestValueIndex = 0;
		int lowestValueIndex = 0;
		int index = 0;
		for (Text value : values) {
			 String line = value.toString();
			 int commaIndex = line.indexOf(',');
			 
			 float date = Float.parseFloat(line.substring(0,commaIndex));
			 float rating = Float.parseFloat(line.substring(commaIndex+1));
			
			 dates.add(date);
			 depressionValues.add(rating);
			 
			 if(rating > depressionValues.get(highestValueIndex)) highestValueIndex = index;
			 else if(rating < depressionValues.get(lowestValueIndex)) lowestValueIndex = index;
				 			 
			 index++;
			 
		}
		
		
		float slope = bestFitLineSlope(dates, depressionValues);
		
		String label = "Best fit line algorithm of data " + key + "has depression value";
		if(key.toString().equals("All Categories")) label = "Over all the Categories, best fit line has depression";
		
		
		String resultString = " increasing at a rate of " + slope;
		if(slope < 0 ) resultString = " decreasing at a rate of " + slope;
		
		resultString += " since April. The highest depression rating was recorded on ";
		resultString += dayToDateConversion(dates.get(highestValueIndex));
		resultString += " with value " + depressionValues.get(highestValueIndex);
		resultString += ", and the lowest depression rating was recorded on ";
		resultString += dayToDateConversion(dates.get(lowestValueIndex));	
		resultString += " with value " + depressionValues.get(lowestValueIndex);
		resultString += ".";
		
		context.write(new Text(label), new Text(resultString));
	 }
	
	public static String dayToDateConversion(float date) {
		
		/*offset to begin in april because no data from beyond then
		 * */
		float value = date - 90;
		
		if(value <= 30) return "April-"+value;	
		value -= 30;
		if(value <= 31) return "May-"+value;
		value -= 31;
		if(value <= 30) return "June-"+value;	
		value -= 30;
		if(value <= 31) return "July-"+value;	
		value -= 31;
		if(value <= 31) return "August-"+value;	
		value -= 31;
		if(value <= 30) return "September-"+value;	
		value -= 30;
		if(value <= 31) return "October-"+value;	
		value -= 31;
		if(value <= 30) return "November-"+value;	
		value -= 30;
		
		return "Error Date";
	}
	
	
	public static float bestFitLineSlope(List<Float> x, List<Float> y) {
		
		float sumDate = 0;
		float sumRating = 0;
		float sumProduct = 0;
		float sumDateSquared = 0;
		for(int i = 0; i < x.size(); i++) {
			
			sumDate += x.get(i);
			sumRating += y.get(i);
			sumProduct += x.get(i) * y.get(i);
			sumDateSquared += x.get(i)*x.get(i);
					
		}
		
		float slope = (x.size() * sumProduct - sumDate * sumRating) / (x.size() * sumDateSquared - sumDate * sumDate);
		return slope;
		
		
	}
	
}