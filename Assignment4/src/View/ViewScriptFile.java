package View;

import java.util.Scanner;

public class ViewScriptFile implements View {

  public ViewScriptFile(){

  }

  @Override
  public String getInput() {
    System.out.println("Please provide the file location of the input script file: ");
    Scanner in = new Scanner(System.in);

    String filePath = in.nextLine();
    in.close();
    return filePath;
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }
}
