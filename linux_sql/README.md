# Linux Cluster Monitoring Agent
# Introduction
The Linux Clustering Monitoring Agent allows users to monitor the clusters/nodes of the specific system which are connected internally through switches. This project takes into consideration two important factors.

1: Hardware Specification :  This factor considers the hardware/storage information of the system.
2. Memory Usage : This factor focuses primarily on CPU usage and utilization over time.
   The project is being build on Linux platform using the containers which are isolated systems along with the SQL scripts. The SQL scripts are built in POSTGRESS  using bash CLI commands to store and access data in real time. Finally, the data is stored in local repository inside the container and a copy of it is stored in public repository (i.e., GitHub) using git commands.
# Quick Start
psql instance using docker
start, stop, and create instance:
./scripts/psql_docker.sh start|stop|create [db_username][db_password]

Creating tables in psql instance
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql

Inserting data into tables for psql instance

A. Hardware Specification (host_info)
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

B. Memory Usage (host_usage)
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

Crontab setup script to automate data
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log

# Implementation
This section briefly describes the arcitecture and implementation of the system.

#Architecture Design















#Scripts
The scripts are written in bash for performing the specified tasks in the project.
Psql_docker.sh: This script is written to initialize a docker container which contains the psql image along with volume data that acts like a physical hard drive and postgres image which performs the basic initialization of psql instance. Finally, the data is being stored in the psql database.
./scripts/psql_docker.sh start|stop|create (db_username)(db_password)

host_info.sh : This script is created to store the static values of the hardware specifications of the system which is further stored in host_info table in psql database.

./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password


host_usage.sh: This script is created to store the dynamic values of memory usage data of the system which is further stored in host_usage table in psql database.

./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password


Crontab: crontab is used to execute memory usage data i.e, host_usage repetedly after certain interval of time and store the values in psql database.

* * * * * bash /path/to/linux_sql/scripts/host_usage.sh psql_host port db_name psql_user psql_password &> /tmp/host_usage.log

#Database Modelling

Host_Info Table Schema:

Attribute Name	Type	Description
id	SERIAL PRIMARY KEY	This is the id for the host i.e., database
hostname	VARCHAR UNIQUE	This is the name of the system
cpu_number	INT	Number of CPU used for the processes
cpu_architecture	VARCHAR	Architecture model used in the system
cpu_model	INT	Type of model used in CPU for the process
cpu_mhz	INT	CPU clock frequency (in MHz)
l2_cache	INT	Size of L2 Caches in the memory
total_memory	INT	Total memory used in the system in the processes
timestamp	TIMESTAMP	The timestamp when values are collected








Host_usage Table:
Attribute Name	Type	Description
timestamp	TIMESTAMP	The timestamp when values are collected
hostid	SERIAL	Host id of the system taken from host_info table and is used as foreign key
memory_free	INT	Free memory in the system
cpu_idle	INT	Amount of time cpu is in ideal state. Calculated in percentage
cpu_kernel	INT	Amount of time processer in kernel mode. Calculated in percentage
disk_io	INT	Number of disk i/o
disk_availaible	INT	Amount of available disk space in system


#Test
The testing methods are used to check the functionality validation and debugging. These testing methods are explained further.
1.	Bash-x : This command is used for debugging process and entails the brief description of the program
2.	Psql docker.sh : The validation of this command is tested on the terminal using bash command and docker volume is used to check if volume data is created which is used as a physical hard drive.
3.	Host_info.sh & host_usage.sh: These commands are tested on local system and verified by checking the psql database if values are stored in the database or not.

#Improvements

