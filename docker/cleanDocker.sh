#!/bin/bash -x 
# Stop and remove all entries in the Docker environment

# Stop all containers
docker stop $(docker ps -a -q)

# Delete all containers
docker rm $(docker ps -a -q)

# Delete all images
docker rmi $(docker images -q)

# Delete all volumes
docker volume rm $(docker volume ls -q)

