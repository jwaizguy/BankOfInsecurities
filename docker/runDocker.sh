#!/bin/bash
 
# Script Name: runDocker.sh
# This script builds and deploys the docker images for 
# the server part of our project.

echo "Bank Of Insecurities"

# Check to see if docker is installed
command -v docker >/dev/null 2>&1 || { 
	echo >&2 "This script requires docker to be installed."; 
	echo "Please install docker and try again.";
	echo "Script will exit now...";
	exit 1; 
}

db_server_image="cigital/boi_db_server"
db_server_container="boi_db_server"
db_script="Dockerfile.db"

web_server_image="cigital/boi_web_server"
web_server_container="boi_web_server"
web_script="Dockerfile.web"

tomcatPortNo=8080

# Building postgres image
echo
echo "===== Building PostgreSQL image: $db_server_image ====="
docker build -q -t "$db_server_image" -f "$db_script" .
echo

# Building Tomcat image
echo
echo "===== Building Tomcat image: $web_server_image ====="
docker build -q -t "$web_server_image" -f "$web_script" .
echo

# Check if container already exists, and remove it
tomcatContainerID="$(docker ps -a | \
	grep "\s$web_server_container$" | grep -Eo '^[^ ]+')"
postgresContainerID="$(docker ps -a | \
	grep "\s$db_server_container$" | grep -Eo '^[^ ]+')"

if [[ ! -z $tomcatContainerID ]]; then
	echo "===== Removing existing $web_server_container container ====="
	docker rm -f "$web_server_container"
	echo
fi
if [[ ! -z $postgresContainerID ]]; then
	echo "===== Removing existing $db_server_container container ====="
	docker rm -f "$db_server_container"
	echo
fi

echo "===== Starting $db_server_container container ====="
docker run --hostname $db_server_container --name "$db_server_container" -p 5432:5432 -e POSTGRES_USER=boi -e POSTGRES_PASSWORD=boi123 -e POSTGRES_DB=cigital_boi -d "$db_server_image"
echo 
echo "===== Starting $web_server_container container ====="
docker run --hostname $web_server_container --name "$web_server_container" -d -p "$tomcatPortNo":8080 --link "$db_server_container":"$db_server_container" "$web_server_image"

echo
echo "===== Displaying Bank Of Insecurities containers ====="
docker ps -a | grep "boi"
echo
echo "===== Exiting runDocker script ====="

