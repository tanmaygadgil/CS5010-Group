package imgeditor.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class represents a BMPImageIOHandler.
 * Specifically, it represents a BMPImageIOHandler which supports bitmap image files.
 * This class contains methods for reading the image data from an InputStream and
 * writing the pixel array to the provided OutputStream.
 */
class BMPImageIOHandler extends AbstractRasterImageIOHandler {

  @Override
  public ImageData read(InputStream inputStream) throws IOException {
    return readHelp(inputStream, 3);
  }

  @Override
  public void save(OutputStream out, ImageData imageData) throws IOException {
    saveHelp(out, imageData, "bmp");
  }
}