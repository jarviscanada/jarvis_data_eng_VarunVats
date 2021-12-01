import org.apache.spark.sql.SparkSession


object Hive {

  def main(args: Array[String]): Unit = {
    // spark session

    //System.setProperty("hadoop.home.dir","C:\\winutils")
    val spark = SparkSession
      .builder()
      .appName("Hello Spark")
      .config("spark.master","local")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    println("Create Spark Session")

    //spark.sql(""" CREATE DATABASE hive_data""")
    // spark.sql(""" USE hive_data""")
    spark.sql(
      """ CREATE TABLE IF NOT EXISTS src (foo String, bar LONG, baz BIGINT)
        |ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
        |""".stripMargin)
    spark.sql(""" INSERT INTO src VALUES("Rohit", 100, 20)""")









  }

}
