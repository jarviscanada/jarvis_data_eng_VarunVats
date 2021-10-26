#! /bin/sh
cmd=$1
db_username=$2
db_password=$3
sudo systemctl status docker || systemctl start docker
docker container inspect jrvs-psql
container_status=$?
case $cmd in 
  create)
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1	
	fi
if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi
docker volume= create pgdata
docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
exit $?
	
 if [ $container_status -eq 0 ]; then
		echo 'Container is created'
		exit 1	
fi
docker container stop jrvs-psql
docker container start jrvs-psql

docker ps -f name=jrvs-psql
sudo yum install -y postgresql
export PGPASSWORD='password'
psql -h localhost -U postgres -d postgres -W
psql -h HOST_NAME -p 5432 -U USER_NAME
psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME
export PGPASSWORD=USER_PASSWORD
psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME
psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME -f FILE_NAME.sql
psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME -c FILE_NAME.sql
/bin/systemctl stop postgresql

	exit $?	
  
  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
esac 
