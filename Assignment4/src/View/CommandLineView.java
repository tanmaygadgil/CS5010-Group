package View;

import java.util.Scanner;

public class CommandLineView implements View {

  @Override
  public String getInput() {
    Scanner in = new Scanner(System.in);

    return in.nextLine();
  }

  @Override
  public void renderOutput(String inputString) {

  }
}
