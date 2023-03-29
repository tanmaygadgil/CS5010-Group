# USE Me

This file will outline the necessary step to use this application, all accepted commands 
and a few sample commands to run the files 

## Commands

### Accepted commands

Here is a list of accepted commands for both the command mode and the script mode.

1. load <image-path> <image-name>
2. save <image-path> <image-name>
3. greyscale <image-name> <dest-image-name>
4. horizontal-flip <image-name> <dest-image-name>
5. vertical-flip <image-name> <dest-image-name>
6. brighten <increment_value> <image-name> <dest-image-name>
7. rgb-split <image-name> <dest-image-name-red> <dest-image-name-green> <dest-image-name-blue>
8. rgb-combine <image-name> <red-image> <green-image> <blue-image>
9. dither <image-name> <dest-image-name>
10. sepia <image-name> <dest-image-name>
11. gaussian-blur <image-name> <dest-image-name>
12. sharpen <image-name> <dest-image-name>

The main script of this application is run using the Main.java class found in the src/ folder

The first step is to compile the classes to ensure that they run in the terminal
To compile the code

#### Before running the script:

1. First be in the [src/](src) directory
    ```shell
   cd src/
   ```
2. Run the compilation script
    ```shell
    sh compile_all.sh
    ```

#### Running the Script:

**Command line mode**

In this mode the app runs when the user gives command line inputs into the terminal
To run the script:

```shell
cd src/
```

```shell
java Main.java
```

To exit the application enter the command ```exit``` or use ```ctrl-c```

**Scripting mode**

This mode accepts a script to run. This script is a text file which lists out all the
commands that can be given in the terminal. To add a comment add in text following a ```#```
character

To run the application script mode attach the -s flag to the command line arguments

```shell
cd src/
```

```shell
java Main.java -s <location of script file.txt>
```

### Sample Scripts

Run these sample scripts for the following use cases

#### Running in command mode

First execute

```shell
>>> cd src/
>>> java Main.java
```

Then execute the following commands **one at a time**

```shell
>>> load sample.ppm scene

>>> vertical-flip scene scene-vertical

>>> rgb-split scene scene-red scene-green scene-blue

>>> save scene-red.ppm scene-red

>>> save scene-green.ppm scene-green

>>> save scene-blue.ppm scene-blue
```

#### Running in script mode

```shell
>>> cd src/
>>> java Main.java -s testScript.txt
```

