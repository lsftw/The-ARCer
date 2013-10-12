@echo off

mkdir release

echo Copying over dlls, jar, and data folders.
copy lib\lwjgl-2.8.5\native\windows\*.dll release\*.dll

del release\data /Y
robocopy data release\data

del release\level /Y
robocopy level release\level

del release\res /Y
robocopy /E res release\res

del release\thearcer.jar
copy /B thearcer.jar /B release\thearcer.jar /Y

del release\run-game.bat
echo @echo off >> release\run-game.bat
echo java -jar thearcer.jar >> release\run-game.bat
echo pause >> release\run-game.bat

pause