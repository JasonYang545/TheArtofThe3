import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;
public class CleanMapper
extends Mapper<Object, Text, Text, Text>
{
//private static final IntWritable one = new IntWritable(1);
public void map(Object key, Text value, Context context)
throws IOException, InterruptedException {
String line = value.toString();
String[] output = line.split(",");
if(!line.contains("PERIOD"))
{
if( output[30].length()!=0 || line.contains("3PT" ))
{
        context.write(new Text(output[4]),new Text( output[14].trim() +"," + output[5] + "," + output[32] + "," +  output[30].trim())) ;
        }
}
}
}
:
