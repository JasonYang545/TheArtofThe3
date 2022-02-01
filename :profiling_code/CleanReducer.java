import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.ArrayList;
import java.util.Collections;
public class CleanReducer
extends Reducer<Text, Text, Text, Text> {
//private IntWritable result = new IntWritable();
public void reduce(Text key, Iterable<Text> values, Context context)
throws IOException, InterruptedException {
//team 1 makes
int miss1  = 0;
//team 1 attempts
int attempts1 = 0;
//team 2 makes
int miss2 = 0;
//team 2 attempts
int attempts2 = 0;
//team 1
String home= "";
//team 2
String away = "";
//team 1 score
int score1 = 0;
//team 2 score
int make1 = 0;
int make2 = 0;
int score2 = 0;
      for (Text val : values) {
        String line = val.toString();
        String[] output = line.split(",");
          // set home team once
        if ((home.length()==0) && output.length!=1 && (output.length!=3) && output.length!=4)
         {
             home = output[0];
         }
         // set away team once
        else if ((away.length()==0) && output.length!=1 && (output.length!=2) && output.length!=4)
         {
             away = output[0];
         }
        if(output.length==2 &&output[1].contains("MISS"))
        {
                miss1++;
                attempts1++;

        }
        if(output.length==3  &&output[2].contains("MISS"))
        {
                miss2++;
                attempts2++;

        }
        if(output.length==4)
        {
                if(output[2].contains("3PT") &&!output[2].contains("MISS"))
                {
                        attempts2++;
                        make2++;
                         score1 = Integer.parseInt(output[3].split("-")[0].trim());
                }
                else if(output[1].contains("3PT") &&!output[1].contains("MISS"))
                {
                                        make1++;
                                        attempts1++;
                                        score2 = Integer.parseInt(output[3].split("-")[1].trim());
                }
                else if(output[1].length()==0 && output[2].length()==0 && output[3].length()!=0)
                {

                        score1 = Integer.parseInt(output[3].split("-")[0].trim());
                        score2 = Integer.parseInt(output[3].split("-")[1].trim());
}

        if(output.length==1 && output[0].contains("-"))
        {
                  score1 = Integer.parseInt(output[0].split("-")[0].trim());
                        score2 = Integer.parseInt(output[0].split("-")[1].trim());
        }
        score1 = Integer.parseInt(output[3].split("-")[0].trim());

                        score2 = Integer.parseInt(output[3].split("-")[1].trim());
}
if(line.contains("-") && line.length()==1)
{
        score1 = Integer.parseInt(output[0].split("-")[0].trim());
                        score2 = Integer.parseInt(output[0].split("-")[1].trim());
}
}
int homeResult = 1;
         int awayResult = 1;
         if  (score1 < score2)
         {
                 awayResult = 0;                                                                                                                                   
          }                                                                                                                                                        
         else if (score1 > score2)
        {
                homeResult = 0;
         }                                                                                                                                                                                                                                    String homeStats = key.toString() + "," + home + "," + String.valueOf(make1) + "," + String.valueOf(miss1) + "," + String.valueOf(attempts1) + "," + String.valueOf(homeResult);
  String awayStats = key.toString() + "," + away + "," + String.valueOf(make2) + "," + String.valueOf(miss2) + "," + String.valueOf(attempts2) + "," + String.valueOf(awayResult);                                                                                                                                                                                                                                                                                                                String blah = homeStats + "\n" + awayStats;

context.write(new Text(blah), new Text(""));
}
}

                                                                                                           }



                                                                                                             }

