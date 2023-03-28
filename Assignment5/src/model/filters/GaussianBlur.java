package model.filters;

public class GaussianBlur extends AbstractFilter{

  public GaussianBlur(){
    this.kernel = new double[][] {
        {1.0/16, 1.0/8, 1.0/16},
        {1.0/8, 1.0/4, 1.0/8},
        {1.0/16, 1.0/8, 1.0/16}
    };
  }

}
