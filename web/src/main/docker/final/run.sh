#!/bin/sh
#down file
https://github.com/Zo3i/frpMgr.git
#enter path
cd ./CodeJsSystem/web/src/main/docker
chmod -R 755 ./*
docker build -t jo/mysql .
docker-compose build
docker-compose up -d