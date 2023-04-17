package imgeditor.model;

/**
 * This is a helper class that represents the data of an image.
 * It contains the 3D-Array of pixels which constitute that image.
 * It offers a method to retrieve a copy of the pixel array.
 */
class ImageData {

  //width height channel
  private final int[][][] pixels;

  /**
   * Construct an ImageData object given the pixel array of the image.
   *
   * @param pixels the pixel array representing the image's pixels
   */
  ImageData(int[][][] pixels) {
    this.pixels = pixels;
  }

  /**
   * This method retrieves a copy of the pixel array field for this object.
   * Direct access to the image's pixels is prevented due to returning a copy.
   * @return a copy of the pixel array
   */
  int[][][] getPixels() {
    int[][][] copyArray = new int[this.pixels.length]
            [this.pixels[0].length][this.pixels[0][0].length];
    for (int i = 0; i < this.pixels[0].length; i++) {
      for (int j = 0; j < this.pixels.length; j++) {
        System.arraycopy(this.pixels[j][i], 0, copyArray[j][i], 0,
                this.pixels[0][0].length);
      }
    }
    return copyArray;
  }

}
