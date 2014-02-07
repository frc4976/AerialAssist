@ECHO off
SET /p val1=How small should the minimap be? (larger num = smaller screen): 
SET /p val2=Are you on the the blue team? (true/false): 
java -jar JMiniMap.jar %val1% %val2%