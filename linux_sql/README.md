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

# Architecture Design

![arcitecture image](https://user-images.githubusercontent.com/47187283/139781701-5e2aa337-abae-49dc-99a0-d5091e6d661a.PNG)













# Scripts
The scripts are written in bash for performing the specified tasks in the project.
Psql_docker.sh: This script is written to initialize a docker container which contains the psql image along with volume data that acts like a physical hard drive and postgres image which performs the basic initialization of psql instance. Finally, the data is being stored in the psql database.
./scripts/psql_docker.sh start|stop|create (db_username)(db_password)

host_info.sh : This script is created to store the static values of the hardware specifications of the system which is further stored in host_info table in psql database.

./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password


host_usage.sh: This script is created to store the dynamic values of memory usage data of the system which is further stored in host_usage table in psql database.

./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password


Crontab: crontab is used to execute memory usage data i.e, host_usage repetedly after certain interval of time and store the values in psql database.

* * * * * bash /path/to/linux_sql/scripts/host_usage.sh psql_host port db_name psql_user psql_password &> /tmp/host_usage.log

# Database Modelling

# Host_Info Table Schema:

![host(info)-Table](https://user-images.githubusercontent.com/47187283/139781067-e0dbecb8-c659-4c0b-997f-b148d8bda073.PNG)












# Host_usage Table Schema:


![Host-usage(Table)](https://user-images.githubusercontent.com/47187283/139781244-2dbcb3ca-e539-4db7-8542-b704c6e4fbbb.PNG)



# Test
The testing methods are used to check the functionality validation and debugging. These testing methods are explained further.
1.	Bash-x : This command is used for debugging process and entails the brief description of the program
2.	Psql docker.sh : The validation of this command is tested on the terminal using bash command and docker volume is used to check if volume data is created which is used as a physical hard drive.
3.	Host_info.sh & host_usage.sh: These commands are tested on local system and verified by checking the psql database if values are stored in the database or not.

# Improvements

