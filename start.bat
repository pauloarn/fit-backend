@ECHO OFF
setlocal
set "JAVA_HOME=C:\Program Files\Amazon Corretto\jdk11.0.21_9"
set "path=%JAVA_HOME%;%path%"
mvn clean install -DskipTests spring-boot:run
PAUSE
