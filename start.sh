#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Add environment variables
export TELEGRAM_TOKEN=$1
export MYSQL_USER=user
export MYSQL_PASSWORD=12345
export MYSQL_ROOT_PASSWORD=12345

# Start new deployment
docker-compose up --build -d
