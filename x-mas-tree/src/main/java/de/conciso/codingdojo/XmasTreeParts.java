package de.conciso.codingdojo;

public enum XmasTreeParts {
    BRANCH('X'),
    SPACE(' '),
    STUMP('I'),
    STAR('*');


    private final char character;

    XmasTreeParts(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
