# USEMe

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
13. darken <increment_value> <image-name> <dest-image-name>

The GUI application also supports all of the above operations.

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
java -jar Assignment6.jar -text
```

To exit the application enter the command ```exit``` or use ```ctrl-c```

**Scripting mode**

This mode accepts a script to run. This script is a text file which lists out all the
commands that can be given in the terminal. To add a comment add in text following a ```#```
character

To run the application script mode attach the -file flag to the command line arguments

```shell
java -jar Assignment6.jar -file <location of script file.txt>
```

**Graphical User Interface**

In this mode, the app is run through a graphical interface in which you will be able to see the
changes you make in real time. This graphical interface also displays the histograms of each
component of the image as well as the intensity.

To run the application with the GUI:

```shell
java -jar Assignment6.jar 
```

The GUI gives the following options

- Load: this button lets you load an image from the local file system
- Save: this button lest you save the currently displayed image
- Call operation: To call an operation, select the appropriate operation and hit apply. Some
  operations require additional inputs. Some special considerations are:
    - Darken and Brighten: A dialog box will appear which will ask for an integer value.
    - RGB Split: Splits the current image into three parts. You will have to select three files in
      the following order: The red, the green and the blue component
    - RGB Combine: This will nto display anything in the image panel. You will have to give 4
      files - the red comp, the green comp, the blue comp and finally the location where the
      combined file is saved.

### Sample Scripts

Run these sample scripts for the following use cases

#### Running in command mode

First execute

```shell
>>> java -jar Assignment6.jar -text
```

Then execute the following commands **one at a time**

```shell
load tanzania.jpg tanzania
brighten 50 tanzania brightentanzania
save brightentanzania.jpg brightentanzania

load tanzania.jpg tanzania
darken 100 tanzania darkentanzania
save darkentanzania.jpg darkentanzania

load tanzania.jpg tanzania
horizontal-flip tanzania horizontaltanzania
save horizontaltanzania.jpg horizontaltanzania

load tanzania.jpg tanzania
vertical-flip tanzania verticaltanzania
save verticaltanzania.jpg verticaltanzania

load tanzania.jpg tanzania
greyscale tanzania greytanzania
save greytanzania.jpg greytanzania

load tanzania.jpg tanzania
rgb-split tanzania redtanzania greentanzania bluetanzania
save redtanzania.jpg redtanzania
save greentanzania.jpg greentanzania
save bluetanzania.jpg bluetanzania

load tanzania.jpg tanzania
rgb-combine combinetanzania redtanzania greentanzania bluetanzania
save combinetanzania.jpg combinetanzania

load tanzania.jpg tanzania
dither tanzania dithertanzania
save dithertanzania.jpg dithertanzania

load tanzania.jpg tanzania
sepia tanzania sepiatanzania
save sepiatanzania.jpg sepiatanzania

load tanzania.jpg tanzania
gaussian-blur tanzania gaussian-blurtanzania
save gaussian-blurtanzania.jpg gaussian-blurtanzania

load tanzania.jpg tanzania
sharpen tanzania sharptanzania
save sharptanzania.jpg sharptanzania
```

#### Running in script mode

```shell
>>> java -jar Assignment6.jar -s  commands.txt
```

