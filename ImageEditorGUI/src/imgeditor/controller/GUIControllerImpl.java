package imgeditor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import imgeditor.controller.commands.Load;
import imgeditor.controller.commands.Save;
import imgeditor.model.Model;
import imgeditor.view.GUIView;

/**
 * This class represents an implementation of the GUIController interface. It receives user input
 * through the GUIView and processes them using the Model. The output is then displayed on the view
 * after the operation execution.
 */
public class GUIControllerImpl implements GUIController {

  private final GUIView guiView;
  private Command currentCommand;
  private final Model imageEditorModel;
  private String imageName;

  /**
   * Construct a GUIControllerImpl object.
   *
   * @param model      the model to be used for performing operations
   * @param jFrameView the view to be used for obtaining user inputs and displaying outputs
   */
  public GUIControllerImpl(Model model, GUIView jFrameView) {
    guiView = jFrameView;
    imageName = "guiImage";
    this.imageEditorModel = model;
  }

  /*
  Helper method for executing operations.
  Calls the model operate method and overwrites the image with the same name.
  Supports optional arguments used by some commands.
  Displays the image and histogram after the operation is executed.
   */
  private void operationHelper(String operationName, String... operationArgs) {
    imageEditorModel.operate(operationName,
        new ArrayList<>(Collections.singletonList(this.imageName)),
        new ArrayList<>(Collections.singletonList(this.imageName)), operationArgs);
    displayImageAndHistogram();
  }

  @Override
  public void setView() {
    guiView.addFeatures(this);
  }

  @Override
  public boolean load() {
    String userInput = guiView.load();
    if (userInput == null) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return false;
    }
    currentCommand = new Load(userInput, this.imageName);
    try {
      currentCommand.execute(imageEditorModel);
      guiView.setExecutionMessage("Image Loaded Successfully");
      displayImageAndHistogram();
      return true;
    } catch (IOException e) {
      guiView.setExecutionMessage("Error in loading image");
      return false;
    }
  }

  @Override
  public boolean save() {
    String userInput = guiView.save();
    if (userInput == null) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return false;
    }
    currentCommand = new Save(userInput, this.imageName);
    try {
      currentCommand.execute(imageEditorModel);
      guiView.setExecutionMessage("Image Saved Successfully");
      displayImageAndHistogram();
      return true;
    } catch (IOException e) {
      guiView.setExecutionMessage("Error in saving image");
      return false;
    }
  }

  @Override
  public void horizontalFlip() {
    operationHelper("horizontal-flip");
    guiView.setExecutionMessage("Horizontal Flip successful");
  }

  @Override
  public void verticalFlip() {
    operationHelper("vertical-flip");
    guiView.setExecutionMessage("Vertical Flip successful");
  }

  @Override
  public void filter() {
    String chosenCommand = guiView.filter();
    if (chosenCommand == null) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return;
    }
    operationHelper(chosenCommand, chosenCommand);
    guiView.setExecutionMessage("Filter applied successfully");
  }

  @Override
  public void colorTransformation() {
    String userInput;
    String chosenCommand = guiView.colorTransformation();
    if (chosenCommand == null) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return;
    }
    if (chosenCommand.equals("greyscale")) {
      userInput = "luma-component";
    } else {
      userInput = chosenCommand;
    }

    operationHelper(chosenCommand, userInput);
    guiView.setExecutionMessage("Color transformation applied successfully");
  }

  @Override
  public void dither() {
    operationHelper("dither");
    guiView.setExecutionMessage("Dithering successful");
  }

  @Override
  public void adjustBrightness() {
    String userInput = guiView.getBrightnessValue();
    try {
      if (userInput.isEmpty()) {
        guiView.displayWarningPopup("Brightness value must be an integer");
        guiView.setExecutionMessage("Operation failed. Brightness value must be an integer");
        return;
      }
    } catch (NullPointerException e) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return;
    }
    try {
      operationHelper("brighten", userInput);
    } catch (NumberFormatException e) {
      guiView.displayWarningPopup("Brightness value must be an integer");
      guiView.setExecutionMessage("Operation failed. Brightness value must be an integer.");
      return;
    }
    guiView.setExecutionMessage("Brightness Adjusted Successfully");
  }

  @Override
  public void greyscale() {
    String userInput = guiView.displayDropdown("greyscale");
    if (userInput == null) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return;
    }
    operationHelper("greyscale", userInput);
    guiView.setExecutionMessage("Greyscale successful");
  }

  @Override
  public void rgbSplit() {
    String userInput = guiView.displayDropdown("rgb-split");
    if (userInput == null) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return;
    }
    List<String> destImageNames = Arrays.asList("red-component", "green-component",
        "blue-component");
    imageEditorModel.operate("rgb-split",
        new ArrayList<>(Collections.singletonList(this.imageName)), destImageNames, userInput);
    boolean isSuccessful = rgbSplitSaveHelper(userInput);
    if (!isSuccessful) {
      return;
    }
    guiView.setExecutionMessage("RGB Split successful");
    displayImageAndHistogram();
  }

  private boolean rgbSplitSaveHelper(String userInput) {
    String originalImageName = this.imageName;
    boolean isSaveSuccessful;
    String[] componentNames = new String[]{"red-component", "green-component", "blue-component"};
    for (String savingComponent : componentNames) {
      this.imageName = savingComponent;
      isSaveSuccessful = save();
      if (!isSaveSuccessful) {
        this.imageName = originalImageName;
        displayImageAndHistogram();
        return false;
      }
    }
    this.imageName = userInput;
    return true;
  }

  @Override
  public void rgbCombine() {
    String originalImageName = this.imageName;
    String[] rgbCombineImageNames = {"Component1", "Component2", "Component3"};

    for (String imgName : rgbCombineImageNames) {
      this.imageName = imgName;
      boolean result = load();
      this.imageName = originalImageName;
      if (!result) {
        displayImageAndHistogram();
        return;
      }
    }
    try {
      imageEditorModel.operate("rgb-combine", Arrays.asList(rgbCombineImageNames),
          new ArrayList<>(Collections.singletonList(this.imageName)));
      guiView.setExecutionMessage("RGB Combine successful");
    } catch (IllegalArgumentException e) {
      guiView.setExecutionMessage("RGB Combine failed. Images have different dimensions.");
    }
    displayImageAndHistogram();
  }

  /*
  Display the image and histogram for the current image name.
   */
  private void displayImageAndHistogram() {
    guiView.displayImage(this.imageName);
    guiView.displayHistogram(this.imageName);
  }

  @Override
  public void mosaic() {
    String strategy = guiView.getStrategy();
    String seeds = guiView.getValue();
    try {
      if (strategy.isEmpty()) {
        guiView.displayWarningPopup(
            "Strategy must not be empty - 'random' is the only supported strategy ");
        guiView.setExecutionMessage("Operation failed. Strategy value must be a non empty string");
        return;
      }

      if (seeds.isEmpty()) {
        guiView.displayWarningPopup("Seed value must be an integer");
        guiView.setExecutionMessage("Operation failed. Seed value must be an integer");
        return;
      }
    } catch (NullPointerException e) {
      guiView.setExecutionMessage("User Cancelled Operation. Display Not Changed.");
      return;
    }
    try {
      operationHelper("mosaic", strategy, seeds);
    } catch (NumberFormatException e) {
      guiView.displayWarningPopup("Strategy must not be empty and seed value must be an integer");
      guiView.setExecutionMessage(
          "Operation failed. Strategy must not be empty and seed value must be an integer");
      return;
    }
    guiView.setExecutionMessage("Mosaic Applied Successfully");
  }

}
