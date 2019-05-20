#!/bin/sh
#down file
https://github.com/Zo3i/frpMgr.git
#enter path
cd ./frpMgr/web/src/main/docker/final
chmod -R 755 ./*
docker build -t jo/mysql .
docker-compose build
docker-compose up -d