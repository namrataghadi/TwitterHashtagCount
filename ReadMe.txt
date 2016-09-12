Notes on what the code does:
1] Read the live Twitter Stream
2] Create a checkpoint for Usage of stateful transformations. In this project, I have used updateStateByKey to keep track of the Hashtags and their counts

Data Visualization:
For data visualization, I have used Zeppelin. Zeppelin is an open source tool for doing "on-the-fly" data analysis. It uses inbuilt interpreters for visualizing the results in different languages. In this project, I have used %sql interpreter for querying the TempTable and displaying the results in a grid.

 Instructions to install Zeppelin are below. 
There are two ways the results can be visualized:
Method 1:
1] Persist the results to a output location (typically HDFS.. In our case, local directory)
2] Use Zeppelin to display results

Method 2:
NOTE: this code is commented in the TwitterTrending.scala
Display the intermediate results in the Console on the driver using DataFrame


Instructions to install Zeppelin:
1] Download binary from Apache Zeppelin website:
https://zeppelin.apache.org/download.html
NOTE: MAKE SURE TO DOWNLOAD : Binary package with all interpreters: zeppelin-0.6.0-bin-all.tgz

Untar the file in some location

2] TO START ZEPPELIN:
 cd zeppelin-0.6.0-bin-all
bin/zeppelin-daemon.sh start

3] Now open your favorite browser and type http://localhost:8080



// to shutdown:
bin/zeppelin-daemon.sh stop


STEPS to RUN THE CODE:
 There are two steps. One is to run the Streaming code that does the HashTagCount. Second step is to view the results using Zeppelin.

 To run the hashtag counting code follow:
 1] cd to the folder that contains build.sbt
 2] sbt
 3] compile
 4] run

 This will start the spark streaming job that will calculate the count of distinct hashtags in the current day

 To visualize the results:
 1] Start zeppelin as mentioned above in the instructions
 2] Create a notebook
 3] Use the file in /resources/ZeppelinVisualization.scala
 4] In first window, run the TEMP TABLE CREATION script
 5] In another window in same notebook, run RESULT VISUALIZATION %sql script 

 NOTE: I have included some screenshots of results visualization in Zeppelin.
