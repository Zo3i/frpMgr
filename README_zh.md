[README](README.md) | [中文文档](README_zh.md)

---

# FRP快捷配置面板

### 功能介绍:

- 一键远程安装FRP服务端。
- 可视化配置FRP客户端（支持WEB穿透，RDP远程桌面， 本地目录分享， SSH穿透）
- 查看服务器客户端连接情况。

**DEMO(可能失效): [ http://47.102.210.142:8999/frp ]( http://47.102.210.142:8999/frp )**

感谢**[snuglove]( https://github.com/snuglove )** 提供的demo，服务器有效期最大到2020-11-30 

### 更新日志

- 2019/11/06 新增本地目录分享功能
- 2019/11/04 新增远程桌面功能
- 2019/10/30 新增SSH穿透功能
- 2019/06/27 新增查看客户端连接
- 2019/06/13 新增Frp服务端一键安装
- 2019/02/27 支持Docker一键安装
- 2019/02/15 新增WEB穿透功能
- 2020/07/24 新增win可视化交互bat脚本

### 部署教程

```shell
wget -O - https://raw.githubusercontent.com/Zo3i/OCS/master/docker/docker-all2.sh | sh
wget -O - https://raw.githubusercontent.com/Zo3i/frpMgr/master/web/src/main/docker/final/run.sh | sh
```

**注：代码仅在Centos7,Debian9系统上通过测试**

- 访问:你的服务器 ip:8999/frp 账号 **admin** 密码 **12345678**
- 查看日志命令：1. docker ps 2. docker logs -f --tail 10 java项目的容器ID

> 如果需要修改代码,请自行编译 jar包重新部署即可...

### 使用说明:

- 设置泛域名
- 配置服务器
- 远程安装frp服务到服务器（默认frp控制面板端口为7500，账号密码为：admin，admin）
- 配置客户端
- 下载客户端配置:win 打开open.bat即可,mac 请阅读readme

### 使用教程

- 到购买域名的服务商那设置泛域名解析(如下设置)：
  ![](https://i.bmp.ovh/imgs/2019/06/b8db29874c3b85cf.png)

------

- 设置泛域名对应的服务器
  ![](https://i.bmp.ovh/imgs/2019/06/aad52e0b2b110dc5.png)

------

![](https://i.bmp.ovh/imgs/2019/06/dd24c12ddfa62e4e.png)

- 远程安装frps服务，需填写服务器密码(**无需担心密码泄露，没有保存到数据库。**)

------

- 等待FRP服务安装完成之后，配置客户端配置，即可使用了。

