<div align="center"><h1>Michaelwm's Minimap</h1></div>

This Git Repository contains Michaelwm's JMiniMap software for the FRC4976 Team Robot, for the Aerial Assist FRC Challenge

Each of the files in this repository have a seperate function, which includes:

<dl>
<dt>JMiniMap.jar</dt>
<dd>The central file that handles almost all of the Minimaps functions. This file handles the Graphical User Interface, Mathematics concerning targetting, and I/O with the Driver Station</dd>
<dt>TestController.jar</dt>
<dd>An Optional file for the system, that acts like the sensors on the robot, providing positional I/O data to JMiniMap.jar. This only simulates the robot and is not required to function</dd>
<dt>RUN JMiniMap.bat</dt>
<dd>A Batch file that provides the two parameters that JMiniMap.jar requires to function, the ratio to the screen, and the side that the team is on. This batch file allows the user to easily input these two parameters</dd>
<dt>stationOut.txt</dt>
<dd>A text file that contains the output from the Driver Station and provides input to the JMiniMap</dd>
<dt>minimapOut.txt</dt>
<dd>A text file that contains the output from the JMiniMap and provides input to the Driver Station</dd>
</dl>
