package View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TextInputView extends AbstarctTextView {

  private final String mode;
  private Scanner scanner = new Scanner(System.in);
  private StringGenerator commandGenerator = null;

  public TextInputView(String mode, String filename) throws IOException {
    this.mode = mode;
    if (mode != "script") {
      throw new IllegalStateException("File not accepted if mode is not script mode");
    }

    this.commandGenerator = new StringGenerator(loadFile(filename));
  }

  public TextInputView() {
    this.mode = "command";
  }

  @Override
  public String getInput() {
    Scanner in;
    switch (this.mode) {
      case "script":
        String command;
        if (this.commandGenerator.hasNext()) {
          command = this.commandGenerator.next();
        } else {
          command = "exit";
        }
        return command;
      case "command":
        in = new Scanner(System.in);
        return in.nextLine();
    }

    return null;
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }


}




