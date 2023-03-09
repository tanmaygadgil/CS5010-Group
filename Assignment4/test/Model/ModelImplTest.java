package Model;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

public class ModelImplTest {

  @Test
  public void testSave() throws IOException {
    Model m = new ModelImpl();
    m.load("Koala.ppm", "images/Koala.ppm");
    m.save("test.txt", "Koala.ppm");
  }

}