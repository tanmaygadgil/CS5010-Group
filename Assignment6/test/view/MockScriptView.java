package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class is a mock model of the view for testing.
 */
public class MockScriptView implements View {

  private final Scanner scanner;
  private final String filename;
  private String mode = "command";
  private StringGenerator commandGenerator = null;

  /**
   * Initialize the mock view.
   *
   * @param scanner scanner for inputs.
   */
  public MockScriptView(Scanner scanner) {

    this.scanner = scanner;
    filename = null;
  }

  /**
   * Initialize the mock view.
   *
   * @param scanner  scanner for inputs.
   * @param filename filename to read from.
   * @throws IOException thrown if file is not found.
   */
  public MockScriptView(Scanner scanner, String filename) throws IOException {

    this.scanner = scanner;
    this.filename = filename;
    this.mode = "script";

    this.commandGenerator = new StringGenerator(loadFile(filename));
  }

  @Override
  public String getInput() {
    if (this.mode.equals("command")) {
      return this.scanner.nextLine();
    }
    if (this.mode.equals("script")) {
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

  @Override
  public void reset() {
    //Used as a placeholder for the mock.
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
