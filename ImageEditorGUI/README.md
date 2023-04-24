# Image Editor

## Changes and updates for Assignment 6

The same image as Assignment 4 and 5 has been re-used. The citation is in the last section [Script of commands](#script-of-commands).

Example image is in the `res/` folder. 
Image name: `fox.png`

### Model changes

#### New `ReadOnlyModel` interface and implementation

- Renamed the `TestableImageEditorModel` interface to `ReadOnlyModel`. 
- An implementation of this interface `ReadOnlyModelImpl` has been added. Moved the `getImagePixels` method from the `ImageEditorModel` to this class.
- Now, only the `ReadOnlyModel` will be able to access pixels. This model will be given to the view for retrieving the pixels of an image.

### Controller changes

#### New interface `GUIController`

- This interface represents a Controller for the Image Editor program with a GUI.
- It provides methods for setting up the GUI view and for each operation that is supported.
- It contains all the features that will be provided by the program.
- A new interface was created since any changes made to the old controller will not affect this one, and vice versa since there is no coupling between them.

#### New class `GUIControllerImpl`

- This class implements the `GUIController` interface.
- It receives user input through the `GUIView` and processes them using the Model object.
- The output is then displayed on the view after each operation has been executed.
- Acts as a listener for the view. Any actions performed in the view will call the controller's methods.
- This controller also calls the methods that are needed for displaying the image, histogram or getting any input from the user via the `GUIView`.

### View changes

#### New interface `GUIView`

- This interface represents a GUI View. 
- It offers methods for displaying status messages, warning popup messages and methods for each operation that is supported by the program.
- A new interface was created since the TextView and GUI View have nearly no methods in common.
- This way, for any changes to be made in the GUI and not in the text-based view, only the GUI code will be affected.


#### New class `GUIViewImpl`

- This class implements the `GUIView` interface.
- It contains methods for setting up the GUI frames and panels, along with methods for displaying dialogs and menus for each operation along with displaying the image and histogram.
- It receives user input and directly sends it to the `GUIController`.
- It also contains the logic for the methods required by the controller for getting any additional user input for some operations.


### Added support for additional command line arguments

- `-text`
  - The program will run in interactive scripting mode where the user will be able to type in commands through the command line.
  - Syntax: `java -jar ImageEditor.jar -text`
- `-file`
  - The program will directly execute the commands in the provided script file and end the execution once completed.
  - Syntax: `java -jar ImageEditor.jar -file path_to_script_file.txt`
- No argument passed
  - The GUI will be opened where the user can perform all the previously supported operations.
  - Syntax: `java -jar ImageEditor.jar`


## Changes and updates for Assignment 5

### Controller changes

#### Added support for new commands

- `Blur`
    - This command applies Gaussian Blur on the image.
    - It uses a blur kernel and applies it on the image pixels.
- `Dither`
    - This command dithers the image using the Floyd-Steinberg dithering algorithm.
    - The image is converted to greyscale before applying the algorithm.
- `Greyscale`
    - This command converts the image to greyscale without the user specifying a component while
      giving the command.
    - When no component is specified, it will default to the 'luma' component.
    - The functionality where the component could be specified is still supported.
- `Sepia`
    - This command applies a Sepia color transformation to the image.
    - It uses a sepia kernel and applies it on the image pixels.
- `Sharpen`
    - This command sharpens the image.
    - It uses a sharpening kernel and applies it on the image pixels.

For all commands, the transformed image is stored in the model with the name specified by the user
in the command.

All the command classes' execute methods now call model.operate() and pass a string indicating the
command name instead of calling a specific method from the Model interface.

#### Command line arguments support

- The `ImageEditorRunner` handles a new command line argument `-file`.
- The syntax is as follows: `-file path_to_script_file.txt`
- If the file name is invalid, an exception will be thrown.
- Any files run through the command line argument will run and the program will exit immediately.
- In the future, more command line arguments can be supported easily due to the use of a HashMap to
  map the arguments and their options.

### Model changes

#### `Model` interface updates

- The model interface now contains only three methods: load, save and operate.
- This change is made as now inclusion of any new method won't have any effect on the model.

#### `TestableImageEditorModel` interface

- This interface contains one method getImagePixels.
- It helps us to get access of the pixels stored in the ImageData class while testing and also hide
  the pixels of ImageData class from the Model implementations as it is not necessary
  for those classes to have this method, since it is only used for testing.

#### `ImageEditorModel` updates

- ImageEditorModel class now implements the Model interface as well as the TestableImageEditorModel.
- It implements the TestableImageEditorModel because for thorough testing we needed pixels stored in
  the Image Data and also didn't want to allow clients to have access to the stored pixels.
- So now, the ImageEditorModel contains methods to load, save an image and also an operate method.
- Load: The load method now takes an InputStream and ImageIOHandler instead of the file path as the
  model
  doesn't require the path from which file is to be fetched.
- Save: The save method now takes an OutputStream and ImageIOHandler instead of the file path as the
  model doesn't require the path where the file is to be saved.
- Operate: This method is now used instead of all the methods used to perform operations previously.
  This method retrieves all the required image data objects and performs the specified operation on
  these objects, finally storing the updated image data objects back in the model.
  This helped us to generalize the operations i.e. inclusion of new methods in the project would not
  require inclusion of new method in ImageEditorModel class.

#### New `ImageOperations` interface

- This interface contains one method called perform.
- It is a generic method which gets implemented by the AbstractImageOperations class.

#### New class - `AbstractImageOperations`

- This class gets extended by all the classes representing the operations to be performed.
- It keeps the method from the interface abstract so the concrete classes can override it.
- This class contains a method that provides the alpha component of an image if it exists.
- It also contains a helper method for horizontal and vertical flip which performs the flipping of
  pixels based on the specified flip type.

#### New classes for each command extending `AbstractImageOperations` instead of methods in model implementation

- Previously, we had all the operation methods in the Model and Image interface and all the
  operations were
  performed there.
- Now, we have created different classes for each operation.
- Classes AdjustBrightness, HorizontalFlip, RGBCombine, RGBSplit, VerticalFlip contain
  the same logic which was applied previously in methods in the model created for performing
  specific operations.

#### Updates to Greyscale operation

- If the luma component is specified when the greyscale operation is executed,
  then a Color Transformation operation using the greyscale matrix is performed on the image.
- If any other component is specified, the previous implementation will be used.

#### Renamed Image interface to `ImageIOHandler`

- The Image interface now is called ImageIOHandler as its only task is to read and save the image
  which is basically handling input and output of the image data.
- So now, classes that used to implement Image interface now implements ImageIOHandler.

#### New classes - `JPGImageIOHandler`, `PNGImageIOHandler`, `BMPImageIOHandler`and renamed `PPMImage` to `PPMImageIOHandler`

- These new classes implement the `ImageIOHandler` interface.
- These classes contain read and save methods which basically read and saves the jpg/jpeg, png and
  bmp files respectively.
- The `PPMImageIOHandler`, previously called `PPMImage`, contains the same logic as that of the
  previous assignment.

#### New class - `AbstractRasterImageIOHandler`

- This class contains two methods which is common for the all the classes representing Raster images
  i.e. JPGImageIOHandler, PNGImageIOHandler, BMPImageIOHandler for now.
- This methods will read and save this raster images according to the logic in these methods.

#### New class - `ImageProcessingFactory`

- This is a helper class for the model which provides the object of `ImageOperations` and /
  or `ImageIOHandler`
  based on the provided operation name or image format given to it.
- Any new operations and image formats to be supported will be added to this class.
- Thus, the Model implementation(s) do not need to change in case of new operations or image
  formats.

#### Kernel enum

- This enum class contains all the matrices used for performing filter or color transformation.
- Whenever the filter or color transformation operation is to performed then the matrix representing
  it will be called.

#### Classes added

- `Dither`
    - This class implements the ImageOperations interface and provides functionality to dither an
      image.
    - This class provides a method that converts a normal color image into a black and white
      dithered image
      using the Floyd-Steinberg dithering algorithm.
- `Filter`
    - This class implements the ImageOperations interface and provides functionality to apply filter
      to an image.
    - The filter that can be applied are:-
        - The Image Blur filter that applies a 3x3 filter to every channel of every pixel in an
          image to produce a blurred output image. The filter used is called a "Gaussian" blur.
        - The Image Sharpening filter that accentuates the edges in an image by using a specific
          filter for increasing the contrast between adjacent pixels.
- `ColorTransformation`
    - This class implements the ImageOperations interface and provides functionality to apply color
      transformation to an image.
    - The color transformation functions that can be applied are:-
        - Sepia: A color transformation function that converts a normal color image into a
          sepia-toned image.
        - Greyscale: A color transformation function that converts a normal color image into a luma
          greyscale image.

#### Overview of the changes

- We made changes in the design as in the previous design, for any new functionality to be added, we
  needed to make changes in the interface and also in the class extending that interface for our
  model.
- But now using our new design, we just need to make a new class representing the operation
  implementing the ImageOperations interface in our model and add it to
  the `ImageProcessingFactory`.
- Additionally, if a new image file type is to be supported, we will only need to make an
  additional `ImageIOHandler` implementation for that file type and add it to
  the `ImageProcessingFactory`.
- This way, the number of changes in case of new operations and new file types in our new design is
  minimized compared to the previous design.

### View changes

#### Renamed interface from `View` to `TextView`

- Since GUI and text-based views are so different from each other and are unlikely to have
  many overlapping methods we decided to call this interface TextView, so it refers only to views
  that provide output in the form of text.
- When creating a GUI in the future, we will create a new interface specifically for the GUI with
  relevant methods.

#### Implemented methods in the `TextViewImpl` class

- Previously, the overridden methods in the implementation were left blank.
- We have now implemented the `showSuccessMessage` and `showErrorMessage` methods.
- The view object takes an `Appendable` as an input in its constructor, which is where the messages
  to be shown are appended.
- If `System.out` is passed to the TextView, the `append` method will also print the result in the
  console.
- Having an `Appendable` input also helps us in testing as we can check for errors and successful
  command executions using the object passed in to the view.

### New commands script file

Run the following script file in the res folder to run all the commands in one go.
This file contains all the commands supported by the program, and it stores the output
in the `scriptOutput` folder in the res folder.

The terminal must be in the `res/` folder for the commands in the `script.txt` to work correctly,
as all the paths are relative to the `res/` folder. All the output images from the script file
will be stored in a `scriptOutput` folder inside the `res/` folder.

```
cd res/
java -jar ImageEditor.jar -file script.txt
```

If the above example is run when the terminal is not in the `res/` folder then the images will
not be loaded and saved correctly due to the relative file path being incorrect.

## Assignment 4 content below

<hr>

### Model

<hr>

#### Package `model`

This code defines two interfaces, `Model` and `Image`, and two classes, `ImageEditorModel`
and `PPImage`, which implement each interface to perform different image processing operations.
These actions can be carried out by users using a script file that has been loaded or a command-line
interface. The application provides a `Model` interface to load, save, and other operations like,
brighten, horizontal flip, vertical flip, rgb split, greyscale, and rgb combine operations on
images.
<br>

It is intentional to separate `Model` (for all operations on images) and `Image` (for additional
image processing). This is so that operations may be conducted on multiple types of images depending
on the unique features needed. The majority of image processing activities call for the capacity to
execute operations on all types of images, but some will require specific image types that are
different from others.

It is the primary class in charge of loading, saving, and handling image activities by reading
script file commands. The loaded photos and their information are stored in a HashMap. Overall, the
ImageModel interface and the ImageEditorModel class are used in this program to offer a model for
loading, storing, and processing of images.

It contains the following methods:

- <b>save:</b> Saves the image by taking the image's path and name, writing the image's
  data to the file, then saving the file to the path location.
- <b>load:</b> Loads the image to memory by taking the path and name of the image to load
  and reads the image data from the file.
- <b>horizontalFlip:</b> Flips the image horizontally by swapping the appropriate pixels row-wise.
- <b>verticalFlip:</b> Flips the image vertically by swapping the appropriate pixels column-wise.
- <b>greyscale:</b> This method turns an image into greyscale by extracting the component specified
  as input. The components are as follows: red, green, blue, value, luma, or intensity.
- <b>rgbSplit:</b> By iterating through each pixel in the original picture and setting the
  corresponding pixels in the new images, this method divides the original image into three
  grayscale images, each of which contains the red, green, and blue components separately.
- <b>rgbCombine:</b> By iterating through each pixel in the original images and setting the
  matching pixel in the new image, this method merges the red, green, and blue grayscale images
  into a single image that receives its red, green, and blue components from the three original
  images, respectively.
- <b>brighten:</b> This method iterates over each pixel in the image and modifies the RGB values
  according to the specified amount to increase/decrease the brightness of the image by the desired
  amount.

Private helper methods:

- <b>getImageByType:</b> This method reads the file path of the image and retrieves the appropriate
  Image object from the HashMap on the basis of the extension specified in the file path.
- <b>checkImageNameExistence:</b> Checks for the existence of an image file using the
  specified name.
- <b>putImageName:</b> Stores the image data and name in the HashMap so that it can be
  referenced by the given image name in subsequent commands.
- <b>checkIfObjectIsUnused :</b> If the image name does not refer to the object then
  the object will be removed from memory. This method calls Java's garbage collection method.

<br>

- Class `PPMImage`
    - This class implements the `Image` interface. It is
      responsible for reading and saving PPM (Portable Pixel Map) image files.
      <br>
        - The `read` method takes a file path as input and reads the image data from the specified
          file.
          It
          checks for errors like missing or `invalid file`, `missing`
          or `incorrect image dimensions`,
          and `missing or invalid pixel data`. It then creates and returns an ImageData object
          containing the
          pixel array.

        - The `save` method takes a file path and a pixel array as input and writes the pixel data
          to
          the
          specified file in PPM format. It checks for errors like an invalid file path and writes
          the
          pixel
          values to the file in the correct format.
          <br><br>
        - This class provides basic functionality for reading and writing PPM image
          files.
          <br><br><br>
- Class `ImageTransformation`
    - The `ImageTransformation` class is a helper class that contains methods for performing
      transformations on an image represented as a pixel array. It provides two static methods
      for `flipping` an image `horizontally` and `vertically`, respectively.

    - These methods create a new pixel array by inverting the rows or columns of the original pixel
      array,
      and then copy the pixel values from the original array to the new array in the inverted order.
      The
      new pixel array is then returned as the result of the method. The class is designed to be used
      by the
      image editor program to apply various transformations to images.
    - New methods can also be added in the future.
      <br><br><br>


- Class `ImageManipulation`
    - This class contains methods for performing various manipulations on an image such as
      converting
      an
      image to `grayscale`, adjusting the `brightness` of the image, `splitting` the RGB channels of
      an
      image and `combining` them again. The class contains the following methods:

    - `getGreyscaleValue` - This is a helper method that returns the greyscale pixel value based on
      the component specified. It takes in the component (e.g. "red", "green", "blue", "value", "
      intensity", "luma") and returns the
      corresponding greyscale value. If an invalid component name is provided, it throws an
      `IllegalArgumentException`.

    - `getGreyScaleImage` - This method takes in an array of pixels that represent an image and a
      component for greyscale conversion, and returns a new array of pixels that represent the
      greyscale image. It calls the `getGreyscaleValue` method for each pixel to get its
      corresponding greyscale value.

    - `helpBrighten` - This is a helper method for adjusting brightness. It takes in an adjusted
      pixel brightness value and checks if it goes beyond the minimum or maximum pixel value
      limits (0 and 255 respectively). If it does, it sets the pixel value to the minimum or maximum
      accordingly.

    - `adjustBrightness` - This method takes in an array of pixels that represent an image and an
      integer value for adjusting the brightness of the image. It returns a new array of pixels that
      represent the adjusted image. It calls the helpBrighten method for each pixel to get its
      adjusted brightness value.

    - `getRGBSplitComponent` - This method takes in a component (e.g. "red", "green", "blue")
      and an array of pixels that represent an image and returns a new array of pixels that
      represent only that component of the image.
      It extracts the specified component from the original pixel array and returns it.

    - `combineRGBPixels` - This method takes in three arrays of pixels that represent the red,
      green, and blue channels of an image, and combines them into a single RGB pixel array. It
      returns the combined array.
      <br><br><br>

- Class `ImageData`
    - This is a helper class that represents the data of an image. It contains a
      3D-array
      of pixels which make up the image and offers a method to retrieve a copy of the pixel array to
      prevent direct access to the image pixels. The class constructor takes in the `pixel` array as
      a
      parameter, and the `getPixels()` method returns a copy of the pixel array.

### Controller

<hr>

The controller provides the ability to execute commands either via command line or by providing a
script file.
It handles the execution of the program.

`Controller` <b>Interface</b>
<br>
It defines a single method `execute` which takes a Model object and executes the command on it.

Classes:

- `ImageEditorController` <b>Class:</b> This class acts as the main controller for the entire
  program.
  It receives input from the user, and based on the input, it executes the specified commands.

`Command` <b>Interface:</b> This interface represents a Command. A command defines the type of
operation to be executed on the image.

Classes:

- <b>`Load`:</b> This class implements `Command` interface and is responsible for loading an image
  from a file.

- <b>`Save`:</b> This class implements `Command` interface and is responsible for saving the image
  to a file at the given location.

- <b>`Brighten`:</b> This class implements `Command` interface and is responsible for brightening
  the image as per the value provided.

- <b>`Greyscale`:</b> This class implements `Command` interface and is responsible for converting
  the image to grayscale based on the specified component.

- <b>`VerticalFlip`:</b> This class implements `Command` interface and is responsible for flipping
  the image vertically.

- <b>`HorizontalFlip`:</b> This class implements `Command` interface and is responsible for flipping
  the image horizontally.

- <b>`RGBSplit`:</b> This class implements `Command` interface and is responsible for splitting the
  RGB channels of the image. Splits the given image into three greyscale images containing its red,
  green and blue components respectively.

- <b>`RGBCombine`:</b> This class implements `Command` interface and is responsible for combining
  the RGB channels of the image. Combines the three greyscale images into a single image that gets
  its red, green and blue components from the three images respectively.

## Script of commands

<hr>

- Run the Project
    - Go-To `src`
    - Open file `ImageEditorRunner.java`
    - Run the `public static void main` method
      <br><br>

- Run the following commands <br><br>

    - Load the file `fox.ppm`
    - Fox image source: https://unsplash.com/photos/nOsJYzXEG98
       ```
      load res/fox.ppm fox
      ```

    - rgb-split command, `fox.ppm` file and save one of them at particular location
      with filename `greensplitThroughScript.ppm`.
      ```
      rgb-split fox redsplitThroughScript greensplitThroughScript bluesplitThroughScript
      save res/greensplitThroughScript.ppm greensplitThroughScript
      ```

    - rgb-combine the three red, green, and blue image file, and save the new formed image
      with `RedCombineThroughScript.ppm`.
      ```
      rgb-combine RedCombineThroughScript redsplitThroughScript greensplitThroughScript 
      bluesplitThroughScript
      save res/RedCombineThroughScript.ppm RedCombineThroughScript
      ```

    - brightness, `fox.ppm` file to darken by 50 and save to
      name `decreaseBrightnessThroughScript.ppm`.
      ```
      brighten -30 fox fox-dark
      save res/decreaseBrightnessThroughScript.ppm fox-dark
      ```

    - greyscale, `fox.ppm` file will be change to `value` and `red` component and save as per their
      name.
      ```
      greyscale value-component fox fox-value-greyscale
      save res/valueGreyScaleThroughScript.ppm fox-value-greyscale
      greyscale red-component fox fox-red-greyscale
      save res/redGreyScaleThroughScript.ppm fox-red-greyscale
      ```

    - Flip, `fox.ppm` will change to `vertical` and `horizontal` images.
      ```
      horizontal-flip fox fox-horizontal
      vertical-flip fox fox-vertical
      save res/foxFlippedHorizontallyThroughScript.ppm fox-horizontal
      save res/foxFlippedVerticallyThroughScript.ppm fox-vertical
      ```

    - Run the following script file, to run all above commands in one-go.
      ```
      run res/loadFox.txt
      ```

# SEE [CodeModifications.md](CodeModifications.md) for the code modifications.