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
  call :downloadFunc %downloadWEBUrl% %fileName%
  echo.
) else if %useFor%==2 (
  echo 下载文件目录配置文件...

  if exist file (
     echo "已经存在文件夹file"
     echo.
  ) else (
    md file
  )

  call :downloadFunc %downloadFILEUrl% %fileName%
  echo.
) else (
  echo 下载远程桌面配置文件...
  call :downloadFunc %downloadRDPUrl% %fileName%
  echo.
)

:: 下载
set downloadFrpConfigUrl=%host%/FULL_INI
set frpFullName=frpc_full.ini
call :downloadFunc %downloadFrpConfigUrl% %frpFullName%

set downloadExeUrl=%host%/EXE
set frpExeName=frpc.exe
call :downloadFunc %downloadExeUrl% %frpExeName%
goto:EXIT


::::::::::::::::::::函数::::::::::::::::::::
:: 下载函数
:downloadFunc
  set Save=.\client
  if exist %Save% (echo 位置：%Save%) else (mkdir %Save% & echo 已创建：%Save%)
  set Url=%1
  set SaveName=%2
  for %%a in ("%Url%") do set "FileName=%%~nxa"
    echo 正在下载%FileName%，请勿关闭窗口...
    if not defined Save set "Save=%cd%"
    (echo Download Wscript.Arguments^(0^),Wscript.Arguments^(1^)
    echo Sub Download^(url,target^)
    echo   Const adTypeBinary = 1
    echo   Const adSaveCreateOverWrite = 2
    echo   Dim http,ado
    echo   Set http = CreateObject^("Msxml2.ServerXMLHTTP"^)
    echo   http.open "GET",url,False
    echo   http.send
    echo   Set ado = createobject^("Adodb.Stream"^)
    echo   ado.Type = adTypeBinary
    echo   ado.Open
    echo   ado.Write http.responseBody
    echo   ado.SaveToFile target
    echo   ado.Close
    echo End Sub)>DownloadFile.vbs
    DownloadFile.vbs "%Url%" "%Save%\%SaveName%"
    ::下载完删除生成的vbs文件
    del DownloadFile.vbs
:EXIT
