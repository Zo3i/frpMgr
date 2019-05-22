#! /bin/bash
workspace=$1
filename=$2

# 删除之前的docker
docker stop $filename || true && docker rm $filename || true
docker rmi -f $filename

# 构建docker
cd $workspace
rm -rm ./*
touch Dockerfile
cat>Dockerfile<<EOF
FROM java:8
#VOLUME /tmp
ADD $filename app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EOF

# 生成镜像
docker build -t $filename .

# 启动docker
docker run -d --name $filename -p 8999:8999 $filename
