# PNG2PrusaFW
A simple conversion script to automate the conversion of PNG files for use with Prusa's Marlin firmware, written in Java

Will attempt to convert any files in /src/gui/res/png from .png to .c via bin2cc.py, a util provided with Prusa's offical firmware

# Requirements 
- JDK

# Use 
1. Place any modified images into src/gui/res/png
2. Copy main.java from this repo into the FW's root directory
3. run main.java (use run.bat if on windows)
