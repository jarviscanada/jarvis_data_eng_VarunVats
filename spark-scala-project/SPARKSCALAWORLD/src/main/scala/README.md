Spark-Scala Mini Project

Introduction

This project is based on scala-based platform and is a prototype for managing databases using partitions. The partition is used to cluster data depending on a conditions for ex., if we consider a data from a bank like CIBC then, partiotns can be done on the basis of fiscal year for the customers in order to have a clear understanding regarding their credit records. In this project, we are creating two partition tables i.e., source and a backup. The data in the source is the orignal data and backup consists of the copy of data from the source. However, whenever there is a change in source part partition the program will just copy the contents from source to the backup overwriiting everything that was previously in the backup with the new values.
The technologies used for the project includes 
git,
bash,
CLI,
spark,
scala,
hive, 
Intellij IDEA (debugging and compiling)



Implementation

Psuedo Code

1. To intialize spark session


   SparkSession .builder() .appName("").config("spark.master", "local")
   .enableHiveSupport() .getOrCreate()

2.Creating partition source Table using spark

spark.sql("CREATE TABLE IF NOT EXISTS SOURCE_TABLE(foo String NULL, bar LONG NOT NULL, baz BIGDECIMAL NULL) PARTITION BY(STRING PART) ")

3. Inserting data into source table and display contents

spark.sql("INSERT INTO SOURCE_TABLE VALUES()").show()

spark.sql("SELECT * FROM SOURCE_TABLE").show()

4. Creating partition backup from source table 

spark.sql("CREATE TABLE IF NOT EXISTS BACKUP_TABLE(foo String NULL, bar LONG NOTNULL, baz BIGDECIMAL NULL) PARTITION BY (part String)")

5. Inserting data into backup from source

spark.sql("INSERT INTO BACKUP_TABLE SELECT * FROM SOURCE_TABLE").show()

spark.sql("SELECT * FROM BACKUP_TABLE ").show()

6. Overwritten table to change contents of source if part partition changes

spark.sql("INSERT OVERWRITE TABLE BACKUP_TABLE PARTITION (part) SELECT * FROM SOURCE_TABLE WHERE FALSE")


Test


The functioning of the program has been tested using JUNIT and inserting some dummy data to see the overall functioning of the program.




Improvement

The few improvements in the project can include

1. Providing the data for source to know better functionality
2. Some more columns could be added to the source table



