package imgeditor.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This class represents the implementation of the Model of the image editor program.
 * It contains methods for loading and saving an image using the appropriate image handler
 * and contains a generic method for performing an operation on the image.
 */
public class ImageEditorModel implements Model {
  private final ImageProcessingFactory factory;

  /**
   * Construct a ImageEditorModel object.
   * This constructor initializes the ImageProcessingFactory.
   */
  public ImageEditorModel() {
    this.factory = ImageProcessingFactory.getFactory();
  }

  @Override
  public void load(InputStream in, String imageFormat, String imageName)
          throws IOException {
    ImageIOHandler handler = factory.getImageHandler(imageFormat);
    ImageData imageData = handler.read(in);
    factory.putInImageDataMap(imageName, imageData);
    System.gc();
  }

  @Override
  public void save(OutputStream out, String imageFormat, String imageName)
          throws IOException {
    factory.getFromImageData(imageName);
    ImageIOHandler handler = factory.getImageHandler(imageFormat);
    ImageData imageData = factory.getFromImageData(imageName);
    handler.save(out, imageData);
  }

  @Override
  public void operate(String commandName, List<String> imageName, List<String> destImageName,
                      String... commandArgs) {
    Function<Integer, ImageOperations> operateCmd =
            factory.getImageOperation(commandName);

    List<ImageData> imageData = new ArrayList<>();
    for (String imgName : imageName) {
      imageData.add(factory.getFromImageData(imgName));
    }

    ImageOperations imageOp = operateCmd.apply(0);

    int numberOfDestImageData = 0;
    List<ImageData> updatedImageData = imageOp.perform(imageData, commandArgs);
    for (ImageData resultImageData : updatedImageData) {
      factory.putInImageDataMap(destImageName.get(numberOfDestImageData), resultImageData);
      numberOfDestImageData++;
    }
    System.gc();
  }

}
