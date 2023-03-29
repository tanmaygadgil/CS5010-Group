package controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An IO handler that parses image files and sends the respective input an output readers.
 */
public interface ImageIOHandler {

  InputStream getLoader(String filepath) throws FileNotFoundException;

  OutputStream getSaver(String filepath) throws FileNotFoundException;

  String parseFormat(String filepath);
}
