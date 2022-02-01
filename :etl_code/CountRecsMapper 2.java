import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;
import java.util.Arrays;


public class CountRecsMapper extends Mapper<Object, Text, Text, IntWritable> {
  private final static IntWritable one = new IntWritable(1);

  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    String line  = value.toString();
    if (line != null) {
      context.write(new Text("Total number of lines in Player Data file:"), one);
    }
  }
}