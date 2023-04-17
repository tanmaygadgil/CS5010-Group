package imgeditor.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class represents a PNGImageIOHandler.
 * Specifically, it represents a PNGImageIOHandler which supports png image files.
 * This class contains methods for reading the image data from an InputStream and
 * writing the pixel array to the provided OutputStream.
 */
class PNGImageIOHandler extends AbstractRasterImageIOHandler {

  @Override
  public ImageData read(InputStream inputStream) throws IOException {
    return readHelp(inputStream, 4);
  }

  @Override
  public void save(OutputStream out, ImageData imageData) throws IOException {
    saveHelp(out, imageData, "png");
  }
}
