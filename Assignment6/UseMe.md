# USE Me

This file will outline the necessary step to use this application, all accepted commands
and a few sample commands to run the files

This program runs in three modes

- Text mode: Here twe input the command in the terminal. Commands are highlighted below. This mode
  will be accessed with the -text flag
- Script mode: here the package expects a script file after a flag -file
- UI mode: Spins up a simple UI that can be used to modify a single image displayed in the UI. The
  UI also renders histogram values for the chart.

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
java -jar Assignment5.jar
```

To exit the application enter the command ```exit``` or use ```ctrl-c```

**Scripting mode**

This mode accepts a script to run. This script is a text file which lists out all the
commands that can be given in the terminal. To add a comment add in text following a ```#```
character

To run the application script mode attach the -s flag to the command line arguments

```shell
java -jar Assignment5.jar -s <location of script file.txt>
```

### Sample Scripts

Run these sample scripts for the following use cases

#### Running in command mode

First execute

```shell
>>> java -jar Assignment5.jar
```

Then execute the following commands **one at a time**

```shell
load res/tanzania.jpg tanzania
brighten 50 tanzania brightentanzania
save res/brightentanzania.jpg brightentanzania

load res/tanzania.jpg tanzania
darken 100 tanzania darkentanzania
save res/darkentanzania.jpg darkentanzania

load res/tanzania.jpg tanzania
horizontal-flip tanzania horizontaltanzania
save res/horizontaltanzania.jpg horizontaltanzania

load res/tanzania.jpg tanzania
vertical-flip tanzania verticaltanzania
save res/verticaltanzania.jpg verticaltanzania

load res/tanzania.jpg tanzania
greyscale tanzania greytanzania
save res/greytanzania.jpg greytanzania

load res/tanzania.jpg tanzania
rgb-split tanzania redtanzania greentanzania bluetanzania
save res/redtanzania.jpg redtanzania
save res/greentanzania.jpg greentanzania
save res/bluetanzania.jpg bluetanzania

load res/tanzania.jpg tanzania
rgb-combine combinetanzania redtanzania greentanzania bluetanzania
save res/combinetanzania.jpg combinetanzania

load res/tanzania.jpg tanzania
dither tanzania dithertanzania
save res/dithertanzania.jpg dithertanzania

load res/tanzania.jpg tanzania
sepia tanzania sepiatanzania
save res/sepiatanzania.jpg sepiatanzania

load res/tanzania.jpg tanzania
gaussian-blur tanzania gaussian-blurtanzania
save res/gaussian-blurtanzania.jpg gaussian-blurtanzania

load res/tanzania.jpg tanzania
sharpen tanzania sharptanzania
save res/sharptanzania.jpg sharptanzania
```

#### Running in script mode

```shell
>>> java -jar Assignment5.jar -s res/commands.txt
```

