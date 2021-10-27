#!/bin/bash
#Start docker
sudo systemctl status docker || systemctl start docker
#check container status. If container exists then $exit==2
exist = $(find the list of all runniong docker container)
if [[ $1 == "start" ]] | [[ $1 == "stop" ]] | [[ $1 == "create" ]]; then
case $1 in
start)
    if [[ "$exists" == 2 ]]; then
      docker start "$2"
    else
      echo Cannot able to start the specified container as it is not created
    fi
    exit $?
  ;;
 create)
    if [[ "$exists" == 2 ]]; then #if the container already exists
      echo The container already exists!
    else
       #creates psql container with the given username and password
        docker pull postgres
        docker volume create pgdata
	docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
	docker container ls -a -f name=jrvs-psql
	docker ps -f name=jrvs-psql
	sudo yum install -y postgresql
	export PGPASSWORD='password'
	psql -h localhost -U postgres -d postgres -W
	psql -h HOST_NAME -p 5432 -U USER_NAME
	psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME
	export PGPASSWORD=USER_PASSWORD
	psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME
	psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME -f FILE_NAME.sql


      else #gives error message if username and password is missing
        echo .....
      fi
      exit $?
    fi
    exit 1
  ;; 
