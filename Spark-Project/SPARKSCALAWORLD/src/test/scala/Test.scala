import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

class Test extends FunSuite{

  System.setProperty("hadoop.home.dir", "C:\\winutils")
  val spark = SparkSession
    .builder()
    .appName("Hello Spark")
    .config("spark.master", "local")
    .enableHiveSupport()
    .getOrCreate()






}
