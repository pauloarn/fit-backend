@ECHO OFF
setlocal
set "DB_NAME=FitDataBase"
set "DB_CONNECTION_USER_NAME=root"
set "DB_CONNECTION_PASSWORD=fitAppStrong@123"
set "DB_CONNECTION_HOST=192.168.0.26"

set "JAVA_HOME=C:\Program Files\Amazon Corretto\jdk11.0.21_9"
set "path=%JAVA_HOME%;%path%"
mvn clean install -DskipTests spring-boot:run
PAUSE
