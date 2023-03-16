package View;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MockScriptView implements View {

  private final Scanner scanner;
  private final String filename;
  private String mode = "command";
  private StringGenerator commandGenerator = null;

  //  private InputStream in;
  public MockScriptView(Scanner scanner) {

    this.scanner = scanner;
//    this.in = in;
    filename = null;
  }

  public MockScriptView(Scanner scanner, String filename) throws IOException {

    this.scanner = scanner;
    this.filename = filename;
    this.mode = "script";

    this.commandGenerator = new StringGenerator(loadFile(filename));
//    this.in = in;
  }

  @Override
  public String getInput() {
    switch (this.mode) {
      case "command":
        return this.scanner.nextLine();
      case "script":
        if (this.commandGenerator.hasNext()) {
          return this.commandGenerator.next();
        } else {
          return "exit";
        }
    }
    return "exit";
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }

  private ArrayList<String> loadFile(String fileName) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    ArrayList<String> commandArgs = new ArrayList<String>();
    String line = reader.readLine();
    while (line != null) {
      String parsedLine = extractCommand(line);
      if (parsedLine != null) {
        commandArgs.add(parsedLine);
      }
      line = reader.readLine();
    }
    commandArgs.add("exit");

    return commandArgs;
  }

  /**
   * @param command Helper function to ignore comments and empty lines
   * @return a parsed string
   */
  private String extractCommand(String command) {
    if (command == null) {
      return null;
    }
    if (command.length() == 0) {
      return null;
    }
    if (command.charAt(0) == '#') {
      //return an empty list
      // consider this to be a comment
      return null;
    }
    return command;
  }

  private class StringGenerator implements Iterator<String> {

    private ArrayList<String> stringList;
    private int index;

    public StringGenerator(ArrayList<String> list) {
      this.stringList = list;
      this.index = 0;
    }

    public boolean hasNext() {
      return index < stringList.size();
    }

    public String next() {
      String result = stringList.get(index);
      index++;
      return result;
    }
  }
}
