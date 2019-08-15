FROM mysql:5.7

#设置免密登录
ENV MYSQL_ALLOW_EMPTY_PASSWORD yes

#将所需文件放到容器中
#COPY setup.sh /mysql/setup.sh
#COPY frp原.sql /mysql/frp原.sql
#COPY privileges.sql /mysql/privileges.sql
#COPY setup.sh /docker-entrypoint-initdb.d
COPY frp.sql /docker-entrypoint-initdb.d
COPY privileges.sql /docker-entrypoint-initdb.d 
#设置容器启动时执行的命令
#CMD ["sh", "/mysql/setup.sh"]
