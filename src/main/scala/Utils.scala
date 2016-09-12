import scala.collection.mutable.HashMap
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTimeZone, DateTime}

object Utils{

	def configureTwitterCredentials(apiKey: String, apiSecret: String, accessToken: String, accessTokenSecret: String) {
	  val configs = new HashMap[String, String] ++= Seq(
	    "apiKey" -> apiKey, "apiSecret" -> apiSecret, "accessToken" -> accessToken, "accessTokenSecret" -> accessTokenSecret)
	  println("Configuring Twitter OAuth")
	  configs.foreach{ case(key, value) =>
	    if (value.trim.isEmpty) {
	      throw new Exception("Error setting authentication - value for " + key + " not set")
	    }
	    val fullKey = "twitter4j.oauth." + key.replace("api", "consumer")
	    System.setProperty(fullKey, value.trim)
	    
	  }
	 
	}

	def getSparkContext(): SparkContext = {
		val conf = new SparkConf().setAppName("Hashtag Count App").setMaster("local[2]").set("spark.executor.memory","3g")
		val sc = new SparkContext(conf)
		sc
	}

	def getNow(): DateTime = {
		val now = new DateTime(Config.TZ)
		now
	}

	def updateFunction(newValues: Seq[Int], runningCount: Option[Int]): Option[Int] = {
	    val newCount = runningCount.map{ c => c + newValues.sum}.orElse{Some(newValues.sum)}
	    newCount
	}

}