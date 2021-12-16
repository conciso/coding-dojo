package de.conciso.codingdojo;

import static de.conciso.codingdojo.XmasTreeParts.*;

public class XmasTreeDrawerImpl implements XmasTreeDrawer {

    @Override
    public String drawXmasTree(int numberOfBranches, boolean withStar) {
        return (numberOfBranches < 0 ? throwException() : drawTree(numberOfBranches, withStar));
    }

    private String drawTree(int numberOfBranches, boolean withStar) {
        return drawStar(numberOfBranches, withStar) + drawBranches(numberOfBranches) + drawStump(numberOfBranches);
    }

    private String drawStar(int numberOfBranches, boolean withStar) {
        return (withStar ? drawPart(SPACE, numberOfBranches - 1) + STAR.getCharacter() + "\n" : "");
    }

    private String drawBranches(int numberOfBranches) {
        return drawPart(BRANCH, numberOfBranches * 2 - 1) + drawBranches(numberOfBranches - 1);
    }

    private String drawStump(int numberOfBranches) {
        return drawPart(SPACE, numberOfBranches - 1) + STUMP.getCharacter();
    }

    private String drawPart(XmasTreeParts part, int offsetTimes) {
        return part.getCharacter() + offsetTimes > 0 ? drawPart(part, offsetTimes - 1) : "";
    }

    private String throwException() {
        throw new IllegalArgumentException();
    }
}
