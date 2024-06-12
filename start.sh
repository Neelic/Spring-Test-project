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

# Start new deployment
docker-compose up --build -d
