FROM mysql:5.7

#设置免密登录
ENV MYSQL_ALLOW_EMPTY_PASSWORD yes

#将所需文件放到容器中
COPY frp.sql /docker-entrypoint-initdb.d
COPY privileges.sql /docker-entrypoint-initdb.d
