#!/bin/bash

docker build -t adams/develop:builder --build-arg UID=$(id -u) --build-arg GID=$(id -g) -f 01-builder.Dockerfile .
docker build -t adams/base:latest -f 02-release.Dockerfile .