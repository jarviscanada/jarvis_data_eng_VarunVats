#!/bin/bash
#Start docker
sudo systemctl status docker || systemctl start docker
#check container status. If container exists then $exit==2
if [[ $1 == "start" ]] | [[ $1 == "stop" ]] | [[ $1 == "create" ]]; then
case $1 in
start)
    if [[ "$exists" == 2 ]]; then
      docker start "$2"
    else
      echo 'Cannot able to start the specified container as it is not created'
    fi
    exit $?
  ;;
 create)
    if [[ "$exists" == 2 ]]; then 
      echo 'The container already exists!'
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
    else 
        echo 'username and password is missing'
      fi
      exit $?
    fi
    exit 1
  ;; 
