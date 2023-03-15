package View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TextInputView implements View {

  private final String mode;
  private Scanner scanner = new Scanner(System.in);

  public TextInputView(String mode) throws IOException {
    this.mode = mode;
  }

  public TextInputView() {
    this.mode = "command";
  }

  @Override
  public String getInput() {
    Scanner in;
    switch(this.mode) {
      case "script":
        System.out.println("Please provide the file location of the input script file: ");
        in = new Scanner(System.in);

        String filePath = in.nextLine();
        in.close();
        return filePath;
      case "command":
        in = new Scanner(System.in);
        return in.nextLine();
    }

    return null;
  }

  private String loadFile(String fileName) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String line = reader.readLine();

    return line;
  }

  private String parseCommandLine() {
    return this.scanner.nextLine();
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }
}
