%1 start "" mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

@echo off
set MYSQL_DIR=%~dp0
rem set MYSQL_DIR=%cd%
 
if not "%MYSQL_HOME%"=="" (
    if not "%MYSQL_HOME%"=="%MYSQL_DIR%" (
        echo MYSQL_HOME=%MYSQL_HOME% != MYSQL_DIR=%MYSQL_DIR%
        goto my_ends
    )
) else set MYSQL_HOME=%MYSQL_DIR%
 
set PATH=%MYSQL_HOME%\bin;%MYSQL_HOME%\lib;%PATH%
echo "MYSQL_HOME = %MYSQL_HOME%"
echo Stop and uninstall mysql server ...
 
mysqladmin -uroot  ping  1>nul 2>nul
@if "%ERRORLEVEL%" == "0" (
    net stop GWY_Mysql2017
    rem sc query GWY_Mysql2017
)
 
mysqld --remove GWY_Mysql2017
 
:my_ends
 
pause
