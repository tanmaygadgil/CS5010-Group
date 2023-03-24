package model;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;

public class ModelImplTest {



  @Test
  public void load() {
    InputStream in = null;
    try {
      in = new FileInputStream("test/Model/testImage.ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      OutputStream out = new FileOutputStream("test/Model/testOutImage.ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    Model m = new ModelImpl();
    OutputStream out = null;
    try {
      m.load(in, "testImage", "ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    //PNG
    try {
      in = new FileInputStream("test/Model/greenland_grid_velo.bmp");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      out = new FileOutputStream("test/Model/testOutImage.bmp");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }



    try {
      m.load(in, "testPNGImage", "png");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try{
      m.save(out, "testPNGImage", "png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
  @Test
  public void loadPNG(){

  }
}