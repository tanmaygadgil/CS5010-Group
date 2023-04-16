package imgeditor.model;

import java.util.List;

/**
 * This class represents a Vertical Flip Image Operation.
 * This operation flips an image vertically.
 */
class VerticalFlip extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {
    return flipHelper(imageData, FlipType.Vertical);
  }
}
