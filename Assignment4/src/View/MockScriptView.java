package View;

import java.io.InputStream;
import java.util.Scanner;

public class MockScriptView implements View {

  private final Scanner scanner;

  //  private InputStream in;
  public MockScriptView(Scanner scanner){

    this.scanner = scanner;
//    this.in = in;
  }
  @Override
  public String getInput() {
    return this.scanner.nextLine();
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }
}
