#!/bin/bash

src_dir=$1
docker run -w /app -v ./$src_dir/:/app/ -v ./maven-data/:/root/.m2/ maven:3.9-eclipse-temurin-17-alpine mvn clean package -D skipTests
