# Code Modifications

## Goal and Result

The main premise of this assignment was to practice communicating with another team and use their
code to implement something new. Such a scenario is very common in the real world.

We were able to **successfully** implement image mosaicking in the GUI,script and text mode.

## Code Additions and changes

### Model

The model was design was modular an easy to extend. We needed to make very minimal code changes for
the project to work.

- [ImageMosaicking.java](src%2Fimgeditor%2Fmodel%2FImageMosaicking.java): This file contains the
  main operation that performs image mosaicking. It extends
  the [AbstractImageOperations.java](src%2Fimgeditor%2Fmodel%2FAbstractImageOperations.java)
  interface which provides an easy to use interface for several operations. The image data was also
  packages into the [ImageData.java](src%2Fimgeditor%2Fmodel%2FImageData.java) class which was the
  main data structure used to transfer image data.
- [ImageProcessingFactory.java](src%2Fimgeditor%2Fmodel%2FImageProcessingFactory.java): This file
  acts as the factory class that generates the opertation to be executed for the model. We needed to
  add the mosaicking operation to the hashmap (line 43)
- [PickStrategy.java](src%2Fimgeditor%2Fmodel%2FPickStrategy.java): An interface to generate random
  points.
- [RandomUniformPick.java](src%2Fimgeditor%2Fmodel%2FRandomUniformPick.java): A strategy that
  implements the PickStrategy. this methods generates 2D points sampled uniformly from a R2 space.
- [kdtree](src%2Fimgeditor%2Fmodel%2Fkdtree): We use a KDTree to perform clustering and this
  directory contains the code for all the KDTree packages.

### Controller

The Text Controller was implemented with the command design pattern and we had to make the following
modification to make it work.

- [ImageMosaicking.java](src%2Fimgeditor%2Fcontroller%2Fcommands%2FImageMosaicking.java): We added a
  command in the [commands](src%2Fimgeditor%2Fcontroller%2Fcommands) folder. This command is
  responsible for calling the model operation.
- [ImageEditorController.java](src%2Fimgeditor%2Fcontroller%2FImageEditorController.java): We added
  and entry into the hashmap that holds all the commands called by the model.

The GUI Controller has a slightly different implementation.

- [GUIController.java](src%2Fimgeditor%2Fcontroller%2FGUIController.java): We extended the
  GUIController interface by adding a mosaic operation.
- [GUIControllerImpl.java](src%2Fimgeditor%2Fcontroller%2FGUIControllerImpl.java): We added code
  here to
    - Get additional information from the user via dialog boxes.
    - Call the model to perform the actual operation

### View

The text view required no modifications

We modified [GUIViewImpl.java](src%2Fimgeditor%2Fview%2FGUIViewImpl.java) in the following ways:

- All operations have their own buttons. We added a button in the button panel for the mosaicking
  operation.
- We mapped the functioning of the button to the appropriate method in the controller in the
  addFeatures method
- We had to implement the getValue and getStrategy methods to extract information from the pop-up
  windows.