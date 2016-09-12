//create  temptable for output generated by the SparkStreaming job.
// TEMP TABLE CREATION
val hashtagcount = sc.textFile("/Users/nghadi/Desktop/Interviews/Shopkick/Output/").map{x =>
	 val row =  x.split(",")
	 if(row.size > 1)
	 (row(0), row(1))
	else
	(row(0),"0")
}
import sqlContext.implicits._
hashtagcount.toDF("Hashtag", "Count").registerTempTable("HashtagCount")

// In another window:
// RESULT VISUALIZATION 
// NOTE : Re-Runnign the select statement below, will display latest count everytime.
%sql select * from HashtagCount

