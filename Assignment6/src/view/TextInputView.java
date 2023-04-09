package view;

import java.io.IOException;
import java.util.Scanner;

/**
 * TextInputView is a class that extends AbstractTextView and allows for user input through a
 * command line interface. It supports two modes: "script" and "command". In "script" mode, commands
 * are generated from a file and executed sequentially until the file ends or the command "exit" is
 * reached. In "command" mode, the commands are given through the command line.
 */
public class TextInputView extends AbstractTextView {

  private final String mode;
  private Scanner scanner = new Scanner(System.in);
  private StringGenerator commandGenerator = null;

  /**
   * Initialize the view in script mode.
   *
   * @param mode     the mode to run this view in.
   * @param filename name of the file to be read.
   * @throws IOException thrown if file does not exist.
   */
  public TextInputView(String mode, String filename) throws IOException {
    this.mode = mode;
    if (!mode.equals("script")) {
      throw new IllegalStateException("File not accepted if mode is not script mode");
    }

    this.commandGenerator = new StringGenerator(loadFile(filename));
  }

  /**
   * Initialize the view in command mode.
   */
  public TextInputView() {
    this.mode = "command";
    System.out.println("Please insert your commands here: \n");
  }

  @Override
  public String getInput() {
    Scanner in;

    if (this.mode.equals("script")) {
      String command;
      if (this.commandGenerator.hasNext()) {
        command = this.commandGenerator.next();
      } else {
        command = "exit";
      }
      return command;
    }
    if (this.mode.equals("command")) {
      return this.scanner.nextLine().strip();
    }

    return null;
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }

  @Override
  public void reset() {

  }


}




