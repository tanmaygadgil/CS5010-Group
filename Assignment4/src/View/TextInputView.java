package View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TextInputView implements View {

  private final String mode;
  private Scanner scanner = new Scanner(System.in);
  public TextInputView(String mode, String fileName){
    this.mode = mode;
    if(this.mode.equals("script")){
      String[] commands = loadFile(fileName)
    }
  }

  public TextInputView(){
    this.mode = mode;
  }
  @Override
  public String getInput() {
    if (this.mode.equals("script")){
      return loadAndParseFile()
    }
    return null;
  }

  private String loadFile(String fileName) throws FileNotFoundException {
    reader = new BufferedReader(new FileReader(fileName));
    String line = reader.readLine();

  }

  private String parseCommandLine() {
    return this.scanner.nextLine();
  }
  @Override
  public void renderOutput(String inputString) {

  }
}
