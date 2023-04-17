package imgeditor.model;

import java.util.List;

/**
 * This class represents a Horizontal Flip Image Operation.
 * This operation flips an image horizontally.
 */
class HorizontalFlip extends AbstractImageOperations {

  @Override
  public List<ImageData> perform(List<ImageData> imageData, String... operationArgs) {
    return flipHelper(imageData, FlipType.Horizontal);
  }
}
