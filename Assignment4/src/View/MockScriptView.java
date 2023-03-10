package View;

public class MockScriptView implements View {
  private final String filepath;
  public MockScriptView(String filepath){
    this.filepath = filepath;
  }
  @Override
  public String getInput() {
    return filepath;
  }

  @Override
  public void renderOutput(String inputString) {
    System.out.println(inputString);
  }
}
