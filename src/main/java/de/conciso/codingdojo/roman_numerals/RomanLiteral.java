package de.conciso.codingdojo.roman_numerals;

public enum RomanLiteral {

    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    private int value_;

    RomanLiteral(int i) {
        value_ = i;
    }


    public int getValue() {
        return value_;
    }
}
