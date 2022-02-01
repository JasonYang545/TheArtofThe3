import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CleanReducer extends Reducer<Text,Text,Text,Text> {

    public void reduce (Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
         int maxAttempt = 0;
         String maxText = null;

         for (Text txt : values) {
           String[] valArr = txt.toString().split(",");
             int int_val = Integer.parseInt(valArr[2]);

             if (int_val > maxAttempt) {
               maxAttempt = int_val;
               maxText = txt.toString();
             }
         }
         context.write(new Text(key.toString() + "," + maxText), new Text(""));
    }
}