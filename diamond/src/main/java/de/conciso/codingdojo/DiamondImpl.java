package de.conciso.codingdojo;

import java.util.Arrays;

public class DiamondImpl implements Diamond {
    @Override
    public String createDiamond(char letter) {
        StringBuilder diamond = new StringBuilder();

        String lowerLetter = String.valueOf(letter).toLowerCase();

        char firstLetter = lowerLetter.charAt(0);
        if (Character.isLetter(firstLetter) && firstLetter >= 'a' && firstLetter <= 'z') {
            int endIndex = (int) firstLetter - 97;

            for (int i=0; i<=endIndex; i++) {
                diamond.append(getSpacesBefore(endIndex - i));
                diamond.append(getCurrentLetter(i + 97));
                diamond.append(getSpacesBetween(i));
                if (!getSpacesBetween(i).isEmpty()) {
                    diamond.append(getCurrentLetter(i + 97));
                }
                diamond.append('\n');
            }

            for (int i=endIndex-1; i>=0; i--) {
                diamond.append(getSpacesBefore(endIndex - i));
                diamond.append(getCurrentLetter(i + 97));
                diamond.append(getSpacesBetween(i));
                if (!getSpacesBetween(i).isEmpty()) {
                    diamond.append(getCurrentLetter(i + 97));
                }
                diamond.append('\n');
            }

        }

        return diamond.toString();
    }

    private String getSpacesBefore(int index) {
        return " ".repeat(Math.max(0, index));
    }

    private String getCurrentLetter(int index) {
        return Character.toString((char) index);
    }

    private String getSpacesBetween(int index) {
        return " ".repeat(Math.max(0, 2 * index - 1));
    }
}