### 部署教程
- 安装 docker,docker-compose
- 下载项目: **[https://github.com/Zo3i/frpMgr](https://github.com/Zo3i/frpMgr)**
- 在 web-src-main-docker 目录下有一个成品的目录,里面所有文件上传到服务器
给权限: **chmod -R 777 ./**
进入到 mysql 文件夹中 build mysql 镜像: **docker build -t jo/mysql .**
1)这个步骤只做一次,创建镜像时已经写入数据库如果重新写镜像,数据库会还原;
2)这里的 jo/mysql 是镜像名字,如需修改,同时需要修改 docker-compose.yml 中的镜像名

- 进入 docker-compose.yml 同级目录执行: **docker-compose build**
- 后台运行: **docker-compose up -d**
- 停止运行: **docker-compose stop**
- 访问:你的服务器 ip:8999/frp 账号 **admin** 密码 **12345678**

如果需要修改代码,请自行编译 jar 脚本替换成品中的 jar 脚本重新部署即可...

### 功能介绍:
**一键配置生成服务端,客户端的 frp 配置文件;**
**DEMO(可能失效): [http://47.88.169.121:8999/frp](http://47.88.169.121:8999/frp)**

### 使用说明:
- 设置泛域名
- 配置服务端
- 下载服务端配置,上传到服务器运行: **nohup ./frps -c ./frps.ini >/dev/null 2>/dev/null &**
- 配置客户端
- 下载客户端配置:win 打开open.bat即可,mac 请阅读readme
