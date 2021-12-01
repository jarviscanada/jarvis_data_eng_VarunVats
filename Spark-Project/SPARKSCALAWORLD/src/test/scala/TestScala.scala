

import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

object TestScala extends FunSuite {

  val spark = SparkSession
    .builder()
    .appName("Hello Spark testing ")
    .config("spark.master", "local")
    .enableHiveSupport()
    .getOrCreate()


  spark.sql(s"DROP TABLE src")
  spark.sql(s""" CREATE TABLE IF NOT EXISTS src (foo STRING,bar BIGINT,baz DECIMAL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' PARTITIONED BY(part String)""")

  spark.sql("set hive.exec.dynamic.partition.mode= nonstrict ")

  spark.sql(s"INSERT INTO src VALUES ('',,,'')").show()

  spark.sql(s"SELECT * FROM src").show()

  spark.sql(s"DROP TABLE backup")

  spark.sql(s"""CREATE TABLE IF NOT EXISTS backup (foo STRING,bar BIGINT,baz DECIMAL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' PARTITIONED BY(part String)""")

  spark.sql(s"INSERT INTO backup SELECT * FROM src").show()

  spark.sql(s"INSERT OVERWRITE TABLE backup PARTITION (part) SELECT * FROM source WHERE TRUE")

  spark.sql(s"SELECT * FROM backup").show()









}
