object TwitterCredentials{
	val API_KEY = "1a32IQXthJjA9I7qAYBYvHERL"
	val API_SECRET = " J7b3Vio98aLW0jxl2StCpIFGLjliJ7DieqslBpYsNKsAg02rmp"
	val ACCESS_TOKEN = "774643724278112256-QYn07KkWQMtyCbUV5yHxo6v5AiZjndH"
	val ACCESS_TOKEN_SECRET = "OyqSLadLZjy3khcnvqTrWkNAPoZN5o7CJanMc7DeiM2UT"
}

import org.joda.time.{DateTimeZone, DateTime}
import java.io._
object Config{
	val CHECKPOINT_DIR = System.getProperty("user.dir") + "/CheckPoint/"
	val OUTPUT_DIR = System.getProperty("user.dir") + "/Output/"
	val BATCHINTERVAL = 10
	val TZ = DateTimeZone.forID("America/Los_Angeles")
}