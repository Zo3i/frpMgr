### 功能介绍:
- 一键配置生成客户端的 frp 配置文件;
- 配置好服务器信息之后可远程安装frp服务到任意一台服务器;
**DEMO(可能失效): [http://47.88.169.121:8999/frp](http://47.88.169.121:8999/frp)**

### 部署教程

```shell
wget -O - https://raw.githubusercontent.com/Zo3i/OCS/master/docker/dockerInstall.sh | sh
wget -O - https://raw.githubusercontent.com/Zo3i/frpMgr/master/web/src/main/docker/final/run.sh | sh
```
**注：代码仅在Centos7系统上通过测试**

- 访问:你的服务器 ip:8999/frp 账号 **admin** 密码 **12345678**

如果需要修改代码,请自行编译 jar 脚本替换成品中的 jar 脚本重新部署即可...

### 使用说明:
- 设置泛域名
- 配置服务器
- 远程安装frp服务到服务器（默认frp控制面板端口为7500，账号密码为：admin，admin）
- 配置客户端
- 下载客户端配置:win 打开open.bat即可,mac 请阅读readme

### 使用教程

- 到购买域名的服务商那设置泛域名解析(如下设置)：
![](https://i.bmp.ovh/imgs/2019/06/b8db29874c3b85cf.png)
---
- 设置泛域名对应的服务器
![](https://i.bmp.ovh/imgs/2019/06/aad52e0b2b110dc5.png)
---
![](https://i.bmp.ovh/imgs/2019/06/dd24c12ddfa62e4e.png)
- 远程安装frps服务，需填写服务器密码（无需担心密码泄露，没有保存到数据库。）
---
- 等待FRP服务安装完成之后，配置客户端配置，即可使用了。