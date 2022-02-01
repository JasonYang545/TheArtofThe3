HDFS DIRECTORY AND CONTAINED FILES EXPLAINATION:

/FinalProject -> contains all datasets and java classes used for MapReduce section of this project

/FinalProject/input -> contains raw datasets "2008Stats.csv" and "2016Stats.csv"

/FinalProject/DataClean -> contains Main, Mapper and Reducer java files of MapReduce program to clean and profile our raw datasets

/FinalProject/DataClean/2008 -> Stores output of DataClean MapReduce on raw "2008Stats.csv"

/FinalProject/DataClean/2016 -> Stores output of DataClean MapReduce on raw "2016Stats.csv"

/FinalProject/DataProfile -> contains Main, Mapper and Reducer java files of MapReduce program that counts records in our raw datasets for initial analysis

/FinalProject/nba2008.csv -> cleaned and joined 2008 csv data from Hive consisting of all team member's table data

/FinalProject/nba2016.csv -> cleaned and joined 2016 data from Hive consisting of all team member's table data





HIVE TABLES:

Player2008 -> table representing 2008 Player data using data from DataClean MapReduce on "2008Stats.csv"

Player2016 -> table representing 2016 Player data using data from DataClean MapReduce on "2016Stats.csv"

nba2008 -> table merging data from Player2008 and teammates 2008 play-by-play team data using LEFT OUTER JOIN ON team 

nba2016 -> table merging data from Player2016 and teammates 2016 play-by-play team data using LEFT OUTER JOIN ON team 





PROJECT REPLICATION STEPS

1. scp raw data into Peel(script in /DataIngest):
scp /Users/jasonyang/Documents/NYU/Fall2021/PBDAA/FinalProject/Data/2008Stats.csv jy2634@peel.hpc.nyu.edu:/home/jy2634

scp /Users/jasonyang/Documents/NYU/Fall2021/PBDAA/FinalProject/Data/2016Stats.csv jy2634@peel.hpc.nyu.edu:/home/jy2634



2. Load raw data from Peel onto HDFS(script in /DataIngest):
hdfs dfs -put 2008Stats.csv /FinalProject/input

hdfs dfs -put 2016Stats.csv /FinalProject/input



3. Compile Java code and create jar file for DataClean and DataProfile  MapReduce jobs (SCREENSHOT Step3.1 and Step3.2):
javac -classpath `yarn classpath` -d . CleanMapper.java
javac -classpath `yarn classpath` -d . CleanReducer.java
javac -classpath `yarn classpath`:. -d . Clean.java
jar -cvf clean.jar *.class

javac -classpath `yarn classpath` -d . CountRecsMapper.java
javac -classpath `yarn classpath` -d . CountRecseducer.java
javac -classpath `yarn classpath`:. -d . CountRecs.java
jar -cvf countRecs.jar *.class



4. Run MapReduce DataProfile CountRecs on raw 2008Stats.csv data (SCREENSHOT Step4.1 and Step4.2):
hadoop jar countRecs.jar CountRecs FinalProject/input/2008Stats.csv /user/jy2634/FinalProject/output

output stored in: FinalProject/output/part-r-00000



5. Run MapReduce DataProfile CountRecs on raw 2016Stats.csv data (SCREENSHOT Step5.1 and Step5.2):
hadoop jar countRecs.jar CountRecs FinalProject/input/2016Stats.csv /user/jy2634/FinalProject/output

output stored in: FinalProject/output/part-r-00000



6. Run MapReduce DataClean Clean on raw 2008Stats.csv data by storing for each team, the player with the highest 3 point shot attempts (SCREENSHOT Step6.1 and Step6.2):
hadoop jar clean.jar Clean FinalProject/input/2008Stats.csv /user/jy2634/FinalProject/DataClean/2008/output

output stored in: FinalProject/DataClean/2008/output/part-r-00000



7. Run MapReduce DataClean Clean on raw 2016Stats.csv data by storing for each team, the player with the highest 3 point shot attempts (SCREENSHOT Step6.1 and Step6.2):
hadoop jar clean.jar Clean FinalProject/input/2016Stats.csv /user/jy2634/FinalProject/DataClean/2016/output

output stored in: FinalProject/DataClean/2016/output/part-r-00000


NOTE: At this point in the project, data has been cleaned and is ready for analytics in Hive and Spark Scala


8. Start Hive:
beeline
!connect jdbc:hive2://hm-1.hpc.nyu.edu:10000/
use jy2634;



9. Create table to store 2008 Player data cleaned in Step 6 (SCREENSHOT 9):
CREATE EXTERNAL TABLE Player2008(team STRING, playerName STRING, threeP INT, threePA INT, threePP DOUBLE, points STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE LOCATION '/user/jy2634/Player2008';



10. Create table to store 2016 Player data cleaned in Step 7 (SCREENSHOT 10):
CREATE EXTERNAL TABLE Player2016(team STRING, playerName STRING, threeP INT, threePA INT, threePP DOUBLE, points STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE LOCATION '/user/jy2634/Player2016';



11. Load 2008 cleaned data from Step 6 into Hive table created in Step 9 (SCREENSHOT 11):
load data inpath 'hdfs://horton.hpc.nyu.edu:8020/user/jy2634/FinalProject/DataClean/2008/output/part-r-00000' overwrite into table Player2008;



12: Load 2016 cleaned data from Step 7 into Hive table created in Step 10 (SCREENSHOT 12):
load data inpath 'hdfs://horton.hpc.nyu.edu:8020/user/jy2634/FinalProject/DataClean/2016/output/part-r-00000' overwrite into table Player2016;



13: Create and load joined table to merge 2008 Player data and teammate's 2008 play-by-play data (SCREENSHOT 13):
CREATE TABLE nba2008 AS SELECT t.gameid, t.team, t.madethrees, t.missedthrees, t.attempts, t.win, p.playername, p.threep, p.threepa, p.threepp, p.points FROM nl1776.2008Stats t LEFT OUTER JOIN Player2008 p ON (t.team = p.team);



14: Create and load joined table to merge 2016 Player data and teammate's 2016 play-by-play data (SCREENSHOT 14):
CREATE TABLE nba2016 AS SELECT t.gameid, t.team, t.madethrees, t.missedthrees, t.attempts, t.win, p.playername, p.threep, p.threepa, p.threepp, p.points FROM nl1776.2016Stats t LEFT OUTER JOIN Player2016 p ON (t.team = p.team);



15. With nba2008 and nba2016 representing the joined team data, execute a series of Hive queries for analytics:
HQL queries stored in file "HiveAnalytics" with respective outputs in labeled "Step15.*" in /screenshots



NOTE: at this point, all Hive query analysis have been completed. The next steps are saving Hive tables as csv onto Peel local for use on Spark Scala



16. For seond half of analysis using Spark Scala, save nba2008 and nba2016 Hive tables to csv on local Peel:
hive -e 'use jy2634; select * from nba2008' | sed 's/[\t]/,/g'  > nba2008.csv

hive -e 'use jy2634; select * from nba2016' | sed 's/[\t]/,/g'  > nba2016.csv



17. Connect to Spark shell:
spark-shell --deploy-mode client --conf spark.ui.port=4444



18. Create RDD in spark reading in nba2008.csv and nba2016.csv stored in local Peel from Step 16:
val nba2008 = sc.textFile("hdfs://horton.hpc.nyu.edu:8020/user/jy2634/FinalProject/nba2008.csv")

val nba2016 = sc.textFile("hdfs://horton.hpc.nyu.edu:8020/user/jy2634/FinalProject/nba2016.csv")



19. Run Scala code stored in file "ScalaAnalytics" to derive analytics:
output and anayltics results illustrated in "Step19.*" in /screenshots









