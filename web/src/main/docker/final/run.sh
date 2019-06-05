#!/bin/sh
rm -rf ./frpMgr
#down file
git clone https://github.com/Zo3i/frpMgr.git
#enter path
cd ./frpMgr/web/src/main/docker/final
chmod -R 755 ./*
cd ./mysql
docker build -t jo/mysql .
cd ..
docker-compose build
docker-compose up -d