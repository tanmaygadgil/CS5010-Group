# Assignment 5

## Group Members

- Tanmay Gadgil
- Seung Hun Lee
- Javad MoeinNajafabadi

## Overview

This is an image processing application that aims to accept user commands either via command line
or by script to perform operations on a PPM image. The design is written as an MVC application.
Details of each component are described below. This version of the application can only handle PPM
images but can be extended for other formats in the future.

At a high level this is how the application functions:

![src](diagrams/src.png)


## Class and Interface Definitions

### Model

![Model](diagrams/Model.png)

The model implements the actual functionalities offered by the program.
In model module we have the following classes:

- **Model:** This interface represents possible actions to be done on an image file. This interface
  has the following methods:

    - load
    - save
    - greyscale
    - flip
    - brighten
    - rgbSplit
    - rgbCombine


- **ModelV2:** This interface extends the Model interface above and adds the ability to call
  filters, color transformations and dithering to an image.
    - callFilter
    - callTransform
    - callOperation


- **loaders** This directory holds the different kinds of loaders needed for different types of
  images.


- **savers** This directory holds the different kinds of savers needed for different types of
  images.


- **filters** This directory contains all the possible filters to which can be applied to an
  image.


- **operations** This directory contains all the possible operations to which can be applied to
  an image.


- **transforms** This directory contains all the possible transforms to which can be applied to
  an image.


- **MockModel:** This interface is a Mock of Model interface for testing the controller easier.
  This interface has the following methods:

    - getLog


- **ModelImpl:** The ModelImpl class represents the image files and provides functionality for
  modifying the image. This class implements Model interface.
  The image is stored as a HashMap where the key is a
  String representing the image name and the value is a three-dimensional array of integers
  representing the red, green and blue pixels.


- **ModelV2Impl** The ModelV2Impl class extends the ModelImpl class and implements the ModelV2
  interface. This class provides all of the functionality of the ModelImpl, but adds the ability
  to call features, color transformations and dithering.


- **Axes:** This enumerated type represents which axe should be considered for flip method.


- **ImageComponents:**  This enumerated type represents which component should be considered
  for the greyscale method.


- **ImageUtil:** This class contains utility methods to read a PPM image from file and simply
  print its contents.

### Controller


![controller](diagrams\controller.png)

The controller takes inputs from the user and tells the model what to do and the view what to show.

- **Controller:** The given code represents an interface called "Controller" in the "controller"
  package of a Java application. The purpose of this interface is to provide a common contract for
  any class that wants to act as a controller in the Model-View-Controller (MVC) architecture.
  The interface has one method called "run()" which takes no arguments and returns void. This method
  is used to execute inputs from the view in a loop.


- **AbstractController:** This class is an abstract controller that implements the Controller
  interface. It provides a set of methods that all controllers need, such as parsing commands,
  calling the model, and rendering the output to the view.


- **ControllerCommandLine:** This class represents the command line implementation of the controller
  interface. It allows users to run commands through a model using a command line interface, and has
  a function called "parseAndCall" which parses input files and calls model functions.


- **ImageIOHandler** An IO handler that parses image files and sends the respective input an output
  readers


- **commands** A directory which contains all the commands our application supports. 


### View

![view](diagrams/view.png)


The view is the part of the program that shows results to the user.

- **View:** This is an interface for a view in a software system, which defines the two methods:
  getInput() to get input in a specific format and renderOutput() to output a string depending on
  the implementation.


- **MockScriptView:**  This is a Java class named "MockScriptView" that implements the "View"
  interface. It is a mock model of the view that is used for testing. It can read from a file and
  simulate user input, depending on the specified mode.


- **TextInputView:** The TextInputView class is a subclass of AbstractTextView and provides a
  command-line interface for user input. It has two modes, "command" and "script", with the former
  taking input directly from the user and the latter executing commands from a file in sequence
  until the end of the file or the command "exit" is reached.

