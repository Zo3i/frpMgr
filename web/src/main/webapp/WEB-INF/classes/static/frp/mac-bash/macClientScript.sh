#!/bin/sh

echo '#########################'
echo '#   author: 张琪灵      #'
echo '#   email: bash@zxx.sh  #'
echo '#########################'

HOST=FIX_HOST
SERVER_DOMAIN=FIX_SERVER_DOMAIN
SERVER_IP=FIX_SERVER_IP
SERVER_PORT=FIX_SERVER_PORT
CONFIG_WEB=$HOST/WEB
CONFIG_FILE=$HOST/WEB
CONFIG_RDP=$HOST/WEB
rm -rf frpc.ini


webFunc() {
  curl -o frpc.ini $1/WEB
  # 输入项目名
  read -p "请输入项目名(随便输入): " project
  sed -i ""  "s/FIX_NAME/$project/g" frpc.ini
  # 输入本地端口
  read -p "请输入本地项目端口(100~60000): " port
  sed -i "" "s/FIX_PORT/$port/g" frpc.ini
  # 输入项目二级域名
  read -p "请输入项目二级域名(英文): " subDomain
  sed -i "" "s/FIX_DOMAIN/$subDomain/g" frpc.ini
  echo -e "\033[31m如需后台运行请执行：nohup ./frpc -c ./frpc.ini >/dev/null 2>/dev/null &\033[0m"
  echo -e "\033[32m内网穿透成功，请访问：$subDomain.$SERVER_DOMAIN:$SERVER_PORT\033[0m"
  ./frpc -c ./frpc.ini
}

fileFunc() {
  curl -o frpc.ini $1/FILE
  # 创建目录
  if [ ! -d "file" ];then mkdir file; fi
  # 输入项目名
  read -p "请输入项目名(随便输入): " project
  sed -i "" "s/FIX_NAME/$project/g" frpc.ini
  # 输入端口
  read -p "请输入访问端口(100~60000): " port
  sed -i "" "s/FIX_PORT/$port/g" frpc.ini
  echo -e "\033[31m如需后台运行请执行：nohup ./frpc -c ./frpc.ini >/dev/null 2>/dev/null &\033[0m"
  echo -e "\033[32m内网穿透成功，把文件放进file文件夹，然后访问：$SERVER_IP:$port/file/ \033[0m"
  ./frpc -c ./frpc.ini
}

rdpFunc() {
  curl -o frpc.ini $1/RDP
  chmod 755 frpc.ini
  # 输入项目名
  read -p "请输入项目名(随便输入): " project
  sed -i "" "s/FIX_NAME/$project/g" frpc.ini
  # 输入端口
  read -p "请输入访问端口(100~60000): " port
  sed -i "" "s/FIX_PORT/$port/g" frpc.ini
  echo -e "\033[31m如需后台运行请执行：nohup ./frpc -c ./frpc.ini >/dev/null 2>/dev/null &\033[0m"
  echo -e "\033[32m内网穿透成功，请访问：$SERVER_IP:$port\033[0m"
  ./frpc -c ./frpc.ini
}

# 下载客户端
if [ ! -f "frpc" ];then echo -e "\033[31m正在下载客户端，请稍后...\033[0m"; curl -o frpc $HOST/MAC; fi
chmod 755 frpc
if [ ! -f "frpc_full.ini" ];then echo -e "\033[31m正在下载frpc_full.ini配置文件，请稍后...\033[0m";curl -o frpc_full.ini $HOST/FULL_INI; fi

# 选择穿透应用（WEB穿透、本地文件分享、远程桌面）
echo "选择穿透应用:"
echo "  1) WEB穿透"
echo "  2) 本地文件分享"
echo "  3) 远程桌面"
echo -n "请输入编号: "

read N
case $N in
  1) echo '本地WEB内网穿透'
    webFunc $HOST
    # 输出链接
  ;;
  2) echo '本地文件分享'
    fileFunc $HOST
    # 输出链接
  ;;
  3) echo '远程桌面'
    rdpFunc $HOST
    # 输出链接
  ;;
esac
