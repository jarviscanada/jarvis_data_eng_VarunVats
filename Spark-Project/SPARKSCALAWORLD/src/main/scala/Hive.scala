
// Importing Necessary Libraries for implementation


import org.apache.spark.sql.SparkSession
// Creating Object as the starting point of the project

object Hive {

  def main(args: Array[String]): Unit = {

    // check the number of arguments

    println("Enter the input argument")

    val source_table = args(0)
    val backup_table = args(1)






    // spark session intialization

    System.setProperty("hadoop.home.dir", "C:\\winutils")
    val spark = SparkSession
      .builder()
      .appName("Hello Spark")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()



    // Creating a source table

    spark.sql(s""" CREATE TABLE IF NOT EXISTS $source_table (foo STRING,bar BIGINT,baz DECIMAL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' PARTITIONED BY(part String)""")


    spark.sql("set hive.exec.dynamic.partition.mode= nonstrict ")

    // Inserting Dummy data into source table

    spark.sql(s"INSERT INTO $source_table VALUES ('Captain America',100,50,'part_1')").show()
    spark.sql(s"INSERT INTO $source_table VALUES('Ant Man',200,90,'part_2')").show()
    spark.sql(s"INSERT INTO $source_table VALUES('Wonder Woman',600,30,'part_3')").show()
    spark.sql(s"INSERT INTO $source_table VALUES ('Spider Man',700,110,'part_4')").show()
    spark.sql(s"INSERT INTO $source_table VALUES('Hero',56,78,'part_11')").show()
    spark.sql(s"INSERT INTO $source_table VALUES('Captain America',100, 50, 'part_13')").show()



    // Display the source table
     spark.sql(s"SELECT * FROM $source_table").show()


    // Creating Backup Table

    spark.sql(s"""CREATE TABLE IF NOT EXISTS $backup_table (foo STRING,bar BIGINT,baz DECIMAL) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' PARTITIONED BY(part String)""")



    // Copying the contents of source into backup

    spark.sql(s"INSERT INTO $backup_table SELECT * FROM $source_table").show()



    // Display the contents of backup table

    spark.sql(s"SELECT * FROM $backup_table").show()

    // Inserting Dummy Data
    spark.sql(s"INSERT INTO $source_table VALUES('hi',3,5,'part_8')")
    spark.sql(s"INSERT INTO $source_table VALUES('James',4,7,'part_11')")
    spark.sql(s"INSERT INTO $source_table VALUES('Jonathan',5,8,'partition1')")
    spark.sql(s"INSERT INTO $source_table VALUES ('Johnny',34,6,'partition_2')")
    spark.sql(s"INSERT INTO $source_table VALUES('Captain America',100,50,'partition_3')")

    // Overwriting the data in backup
    spark.sql(s"INSERT OVERWRITE TABLE backup PARTITION (part) SELECT * FROM $source_table WHERE TRUE")
    spark.sql(s"SELECT * FROM $backup_table").show()



  }
}













































