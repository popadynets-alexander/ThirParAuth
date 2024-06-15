@echo off

echo packaging...
call mvn package -quiet

echo moving package into the destination folder...
cd target
move thirparauth.jar ..\..\..\plugins

echo starting the server...
cd ..\..\..
call .\_start.bat
