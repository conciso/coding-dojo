package de.conciso.codingdojo;

public class XmasTreeDrawerImpl implements XmasTreeDrawer {

  private static final int MAX_BRANCEHS = 10;

  @Override
  public String drawXmasTree(int numberOfBranches, boolean withStar) {
    if (numberOfBranches < 0 || numberOfBranches > MAX_BRANCEHS) {
      throw new IllegalArgumentException();
    }

    StringBuilder xMasTree = new StringBuilder();
    if (withStar) {
      addStar(numberOfBranches, xMasTree);
    }

    for (int level = 1; level <= numberOfBranches; level++) {
      addBranch(numberOfBranches, level, xMasTree);
    }

    addTrunk(numberOfBranches, xMasTree);

    return xMasTree.toString();
  }

  private void addTrunk(int numberOfBranches, StringBuilder xMasTree) {
    xMasTree.append(generateWhiteSpaces(numberOfBranches - 1));
    xMasTree.append("I");
  }

  private void addBranch(int numberOfBranches, int level, StringBuilder xMasTree) {
    xMasTree.append(generateWhiteSpaces(numberOfBranches - level));
    xMasTree.append(generateBranchCharacters((level * 2) - 1));
    xMasTree.append("\n");
  }

  private void addStar(int numberOfBranches, StringBuilder xMasTree) {
    xMasTree.append(generateWhiteSpaces(numberOfBranches - 1));
    xMasTree.append("*\n");
  }

  private String generateBranchCharacters(int amount) {
    return repeat(amount, "X");
  }

  private String generateWhiteSpaces(int amount) {
    return repeat(amount, " ");
  }

  private String repeat(int amount, String string) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < amount; i++) {
      result.append(string);
    }
    return result.toString();
  }

}
