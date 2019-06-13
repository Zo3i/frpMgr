### 部署教程

```shell
wget -O - https://raw.githubusercontent.com/Zo3i/OCS/master/docker/dockerInstall.sh | sh
wget -O - https://raw.githubusercontent.com/Zo3i/frpMgr/master/web/src/main/docker/final/run.sh | sh
```
**注：代码仅在Centos7系统上通过测试**

- 访问:你的服务器 ip:8999/frp 账号 **admin** 密码 **12345678**

如果需要修改代码,请自行编译 jar 脚本替换成品中的 jar 脚本重新部署即可...

### 功能介绍:
**一键配置生成服务端,客户端的 frp 配置文件;**
**DEMO(可能失效): [http://47.88.169.121:8999/frp](http://47.88.169.121:8999/frp)**

### 使用说明:
- 设置泛域名
- 配置服务器
- 远程安装frp服务到服务器
- 配置客户端
- 下载客户端配置:win 打开open.bat即可,mac 请阅读readme
