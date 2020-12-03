package de.conciso.codingdojo;

public class XmasTreeDrawerImpl implements XmasTreeDrawer {

  @Override
  public String drawXmasTree(int numberOfBranches, boolean withStar) {

    StringBuilder result = new StringBuilder();

    if(numberOfBranches<1){
      return "";
    }
    if (withStar)
    {
      for (int i = 0; i < numberOfBranches; i++) {
        result.append(" ");
      }
      result.append("*\n");
    }
    for (int i = 0; i < numberOfBranches; i++) {
      for (int j = 0; j < numberOfBranches-i; j++) {
        result.append(" ");
      }
      for (int j = 0; j < (2*i) + 1; j++) {

        result.append("X");
      }
      result.append("\n");
    }
    for (int i = 0; i < numberOfBranches; i++) {
      result.append(" ");
    }
    result.append("I\n");

    System.out.println(result.toString());
    return result.toString();
  }
}
