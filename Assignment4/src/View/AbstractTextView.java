package View;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractTextView implements View {

  @Override
  public abstract String getInput();

  @Override
  public abstract void renderOutput(String inputString);

  ArrayList<String> loadFile(String fileName) throws IOException {
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
  String extractCommand(String command) {
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

  class StringGenerator implements Iterator<String> {

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
