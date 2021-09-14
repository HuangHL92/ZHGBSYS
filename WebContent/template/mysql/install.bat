%1 start "" mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

@echo off
set MYSQL_DIR=%~dp0
rem set MYSQL_DIR=%cd%
 
if not "%MYSQL_HOME%"=="" (
    if not "%MYSQL_HOME%"=="%MYSQL_DIR%" (
        echo MYSQL_HOME=%MYSQL_HOME% != MYSQL_DIR=%MYSQL_DIR%
        goto my_ends
    )
) else (
    set MYSQL_HOME=%MYSQL_DIR%
    echo MYSQL_HOME must be set as MYSQL_DIR=%MYSQL_DIR%
)
 
set PATH=%MYSQL_HOME%bin;%MYSQL_HOME%lib;%PATH%
 
mysqladmin -uroot  ping 1>nul 2>nul
@if "%ERRORLEVEL%" == "0" (
    echo Mysql is running.
    goto my_ends
)
 
echo "MYSQL_HOME = %MYSQL_HOME%"
echo Install and start mysql server ...
 
mysqld --install GWY_Mysql2017
sc config GWY_Mysql2017 start= DEMAND
rem sc query GWY_Mysql2017
net start GWY_Mysql2017
rem sc query GWY_Mysql2017
 
mysqladmin -uroot  ping 1>nul 2>nul
@if "%ERRORLEVEL%" == "0" echo "Mysql started successfully"
 
:my_ends
 
cmd /K
