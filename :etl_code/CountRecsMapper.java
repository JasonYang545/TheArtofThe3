import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;
public class CountRecsMapper
extends Mapper<LongWritable, Text, Text, IntWritable>
{
private static final IntWritable one = new IntWritable(1);
public void map(LongWritable key, Text value, Context context)
throws IOException, InterruptedException {
String line = value.toString();
String[] output = line.split(",");
if(output!=null)
{
        context.write(new Text("Total number of records in dataset:"), one);

        }

        else
        {
         context.write(new Text("Total number of records in dataset:"), new IntWritable(0));              }
        }
}                                                                                                                                                 
                                                                                                                               
