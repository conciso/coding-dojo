package de.conciso.codingdojo;

public interface XmasTreeDrawer {

    /**
     * The function creates an ascii art xmas tree as String. The result can be used to draw a tree on
     * the screen.
     * <p>
     * Example: A tree with 3 branches with star will be created like this:
     * <p>
     * "  *\n x\n xxx\n xxxxx\n I"
     *
     * @param numberOfBranches number of branches of the Xmas tree
     * @param withStar         <code>true</code> if a star should be drawn at the top of the Xmas
     *                         tree, <code>false</code>
     *                         otherwise
     */
    public String drawXmasTree(int numberOfBranches, boolean withStar);
}
