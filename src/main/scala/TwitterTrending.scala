import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
import sys.process.stringSeqToProcess
import org.apache.spark.sql.SQLContext
import org.apache.spark.rdd.RDD
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTimeZone, DateTime}
import org.apache.spark.streaming.Seconds

import org.apache.spark.streaming.twitter.TwitterUtils


object TwitterTrending extends App{

// Configure Twitter credentials

Utils.configureTwitterCredentials(TwitterCredentials.API_KEY, TwitterCredentials.API_SECRET, TwitterCredentials.ACCESS_TOKEN, TwitterCredentials.ACCESS_TOKEN_SECRET)

val sc = Utils.getSparkContext
val ssc = new StreamingContext(sc, Seconds(Config.BATCHINTERVAL))

//create checkpoint for the UpdateStateByKey
ssc.checkpoint(Config.CHECKPOINT_DIR)

val stream = TwitterUtils.createStream(ssc, None)

val tweetDateFormat = DateTimeFormat.forPattern("EEE MMM dd HH:mm:ss Z yyyy")

//Filter the tweets that are for current day
val tweets = stream.filter {tweet => DateTime.parse(tweet.getCreatedAt.toString,tweetDateFormat).toDate == Utils.getNow.toDate} 

//We want to count the Hashtags in current day. Create the (Hashtag, 1) pair
val hashTagsPairs = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#"))).map((_,1))

//Keep a cumulative count by using the updateStateByKey
val hashTagsRunningCount = hashTagsPairs.updateStateByKey(Utils.updateFunction _)


hashTagsRunningCount.transform{ rdd =>
  rdd.sortBy({case (w, c) => c }, false)
}.foreachRDD{ rdd=>
  //without this it gives "java io task serializable java.io.notserializableexception "
  object SQLContextSingleton {
    @transient private var instance: SQLContext = null

    // Instantiate SQLContext on demand
    def getInstance(sparkContext: SparkContext): SQLContext = synchronized {
      if (instance == null) {
        instance = new SQLContext(sparkContext)
      }
      instance
    }
  }
  //We need a singleton SQL context to make this run in distributed environment
  val sqc = SQLContextSingleton.getInstance(rdd.sparkContext)
  import sqc.implicits._
  //overwrite output to the location
  rdd.map( x => x._1 + " , " + x._2).repartition(1).saveAsTextFile(Config.OUTPUT_DIR)
 
 //Comment the saving to textFile  and enable the code below to see the live results at the interval of BATCHINTERVAL
   /*.toDF("HashTag","Count").registerTempTable("HashTagsCount")
 
  val countHashTags = 
    sqlContext.sql("select *  from HashTagsCount")
  countHashTags.show()*/
}

ssc.start()
ssc.awaitTermination()

}