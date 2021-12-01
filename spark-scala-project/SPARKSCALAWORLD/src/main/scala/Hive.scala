
// Importing Necessary Libraries for implementation


import org.apache.spark.sql.SparkSession
// Creating Object as the starting point of the project

object Hive {

  def main(args: Array[String]): Unit = {

    // check the number of arguments

    if (args.length == 0) {
      println("Enter correct number of arguments")
    }


    // spark session intialization

    System.setProperty("hadoop.home.dir", "C:\\winutils")
    val spark = SparkSession
      .builder()
      .appName("Hello Spark")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()

    println("Create Spark Session")

    // Creating a source table and dropping if it exists previously
    //spark.sql("DROP TABLE src")
    spark.sql(s""" CREATE TABLE IF NOT EXISTS src (foo STRING,bar BIGINT,baz DECIMAL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' PARTITIONED BY(part String)""")


    spark.sql("set hive.exec.dynamic.partition.mode=nonstrict")
    // Inserting Dummy data into source table
    spark.sql("INSERT INTO src VALUES ('Captain America',100,50,'part_1')").show()
    spark.sql("INSERT INTO src VALUES('Ant Man',200,90,'part_2')").show()
    spark.sql("INSERT INTO src VALUES('Wonder Woman',600,30,'part_3')").show()
    spark.sql("INSERT INTO src VALUES ('Spider Man',700,110,'part_4')").show()
    spark.sql("INSERT INTO src VALUES('Hero',56,78,'part_11')").show()
    spark.sql("INSERT INTO src VALUES('Captain America',100, 50, 'part_13')").show()



    // Display the source table
     spark.sql("SELECT * FROM src").show()


    // Creating Backup Table and dropping if it exists previously
    //spark.sql("DROP TABLE backup")
    spark.sql("""CREATE TABLE IF NOT EXISTS backup (foo STRING,bar BIGINT,baz DECIMAL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' PARTITIONED BY(part String)""")



    // Copying the contents of source into backup
    spark.sql("INSERT INTO backup SELECT * FROM src").show()



    // Display the contents of backup table
    spark.sql("SELECT * FROM backup").show()
    spark.sql("INSERT INTO src VALUES('hi',3,5,'part_8')")
    spark.sql("INSERT INTO src VALUES('James',4,7,'part_11')")
    spark.sql("INSERT INTO src VALUES('Jonathan',5,8,'partition1')")
    spark.sql("INSERT INTO src VALUES ('Johnny',34,6,'partition_2')")
    spark.sql("INSERT INTO src VALUES('Captain America',100,50,'partition_3')")

    // Overwriting the
    spark.sql("INSERT OVERWRITE TABLE backup PARTITION (part) SELECT * FROM src WHERE TRUE")
    spark.sql("SELECT * FROM backup").show()



  }
}













































