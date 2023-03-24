package controller;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An IO handler that parses image files and sends the respective input an output readers
 */
public interface ImageIOHandler {
  InputStream load(String filepath);
  OutputStream save(String filepath);
}
