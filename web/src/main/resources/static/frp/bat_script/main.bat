@echo off
REM 声明采用UTF-8编码
chcp 65001
setlocal enabledelayedexpansion


echo.
echo #########################
echo #   author: 张琪灵      #
echo #   email: bash@zxx.sh  #
echo #########################
echo.
echo.

set useFor=
set /p useFor=  用途(1、WEB穿透2、文件目录3、远程桌面)：

::下载
call .\lib\download.bat FIX_DOWNLOAD_URL %useFor%

echo.
echo =====配置文件=====
echo.

if %useFor%==1 (
  :: WEB内网穿透
  set FIX_WEB_NAME=FIX_NAME
  set FIX_WEB_NAME_REPLACE=
  set /p FIX_WEB_NAME_REPLACE=  "请输入项目名(随意)："
  call :modifyFuc !FIX_WEB_NAME! !FIX_WEB_NAME_REPLACE!

  set FIX_WEB_PORT=FIX_PORT
  set FIX_WEB_PORT_REPLACE=
  set /p FIX_WEB_PORT_REPLACE=  "请输入项目端口(100~60000)："
  call :modifyFuc !FIX_WEB_PORT! !FIX_WEB_PORT_REPLACE!

  set FIX_WEB_DOMAIN=FIX_DOMAIN
  set FIX_WEB_DOMAIN_REPLACE=
  set /p FIX_WEB_DOMAIN_REPLACE=  "请输入项目二级域名(英文)："
  call :modifyFuc !FIX_WEB_DOMAIN! !FIX_WEB_DOMAIN_REPLACE!

  echo 公网访问: !FIX_WEB_DOMAIN_REPLACE!.FIX_SERVER_DOMAIN
) ^
else if %useFor%==2 (
  :: 目录文件
  set FIX_FILE_NAME=FIX_NAME
  set FIX_FILE_NAME_REPLACE=
  set /p FIX_FILE_NAME_REPLACE=  "请输入项目名(随意)："
  call :modifyFuc !FIX_FILE_NAME! !FIX_FILE_NAME_REPLACE!

  set FIX_FILE_PORT=FIX_PORT
  set FIX_FILE_PORT_REPLACE=
  set /p FIX_FILE_PORT_REPLACE=  "请输入远程端口(100~60000)："
  call :modifyFuc !FIX_FILE_PORT! !FIX_FILE_PORT_REPLACE!

  echo "把文件放入file文件夹即可分享文件"
  echo "公网访问: FIX_SERVER_IP:!FIX_FILE_PORT_REPLACE!/file/"
) ^
else (
  ::远程桌面
  set FIX_RDP_NAME=FIX_NAME
  set FIX_RDP_NAME_REPLACE=
  set /p FIX_RDP_NAME_REPLACE=  "请输入项目名(随意)："
  call :modifyFuc !FIX_RDP_NAME! !FIX_RDP_NAME_REPLACE!

  set FIX_RDP_PORT=FIX_PORT
  set FIX_RDP_PORT_REPLACE=
  set /p FIX_RDP_PORT_REPLACE=  "请输入远程端口(100~60000)："
  call :modifyFuc !FIX_RDP_PORT! !FIX_RDP_PORT_REPLACE!

  echo "访问：FIX_SERVER_IP:!FIX_RDP_PORT_REPLACE!"
)


:: 运行frp客户端
.\client\frpc.exe -c .\client\frpc.ini

pause
:::::::::::::::::::::::函数:::::::::::::::::::::::

:modifyFuc
  set file=.\client\frpc.ini
  set "file=%file:"=%"
  for %%i in ("%file%") do set file=%%~fi
  echo.
  for /f "delims=" %%i in ('type "%file%"') do (
     set str=%%i
     set "str=!str:%~1=%~2!"
     echo !str!>>"%file%"_tmp.txt
  )

  copy "%file%" "%file%"_bak.txt >nul 2>nul
  move "%file%"_tmp.txt "%file%" >nul 2>nul
  del "%file%"_bak.txt
:EXIT
