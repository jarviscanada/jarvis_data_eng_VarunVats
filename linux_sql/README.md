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
