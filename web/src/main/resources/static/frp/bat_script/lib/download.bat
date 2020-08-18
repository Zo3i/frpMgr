@echo off
color 0b
REM 声明采用UTF-8编码
chcp 65001

if exist client (
   echo "已经存在文件夹client"
   echo.
) else (
  md client
)

::删除文件
if exist .\client\frpc.ini del .\client\frpc.ini
::if exist .\client\frpc.exe del .\client\frpc.exe
if exist .\client\frpc_full.ini del .\client\frpc_full.ini
if exist .\client\frpc.log del .\client\frpc.log

::echo 下载Host: %1
set host=%1
set useFor=%2
set downloadWEBUrl=%host%/WEB
set downloadFILEUrl=%host%/FILE
set downloadRDPUrl=%host%/RDP
set fileName=frpc.ini

if %useFor%==1 (
  echo 下载WEB穿透配置文件...
  call :downFunc %downloadWEBUrl% %fileName%
  echo.
) else if %useFor%==2 (
  echo 下载文件目录配置文件...

  if exist file (
     echo "已经存在文件夹file"
     echo.
  ) else (
    md file
  )

  call :downFunc %downloadFILEUrl% %fileName%
  echo.
) else (
  echo 下载远程桌面配置文件...
  call :downFunc %downloadRDPUrl% %fileName%
  echo.
)

:: 下载
set downloadFrpConfigUrl=%host%/FULL_INI
set frpFullName=frpc_full.ini
call :downFunc %downloadFrpConfigUrl% %frpFullName%

set downloadExeUrl=%host%/EXE
set frpExeName=frpc.exe
if not exist .\client\frpc.exe (
  call :downFunc %downloadExeUrl% %frpExeName%
)

goto:EXIT


::::::::::::::::::::函数::::::::::::::::::::
:: 下载函数
:downFunc
  set Url=%1
  set SaveName=%2
  (powershell "($client = new-object System.Net.WebClient) -and ($client.DownloadFile('%Url%', '.\client\%SaveName%')) -and (exit)") | cmd
