@ECHO OFF
setlocal
set "DB_NAME=FitDataBase"
set "DB_CONNECTION_USER_NAME=root"
set "DB_CONNECTION_PASSWORD=fitAppStrong@123"
set "RUN_FLYWAY=false"
set "DB_CONNECTION_HOST=192.168.0.26"

set "JAVA_HOME=C:\Program Files\Amazon Corretto\jdk17.0.10_7"
set "path=%JAVA_HOME%;%path%"
gradle clean bootRun -x test
PAUSE
