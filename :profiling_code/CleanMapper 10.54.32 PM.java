import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;
import java.util.Arrays;


public class CleanMapper extends Mapper<Object, Text, Text, Text> {
  private final static IntWritable one = new IntWritable(1);

  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
    String line  = value.toString();
    String[] arr = line.split(",");

    // data contains misc info after player name -> remove
    String player = arr[1];
    if (player.contains("\\")) {
      player = player.substring(0, player.indexOf('\\'));
    }

/*
 *  ignore header as writting it imbeds it within data and not as header
 *  this is a problem when creating/inserting into Hive table
 *  will manually insert Header                
 */
    if ((!arr[1].equals("Player")) && (Integer.parseInt(arr[12]) != 0)) {
      context.write(new Text(arr[4].trim()), new Text(player + "," + arr[11].trim() + "," + arr[12].trim() + "," + arr[13].trim() + "," + arr[29].trim()));
    }
  }
}