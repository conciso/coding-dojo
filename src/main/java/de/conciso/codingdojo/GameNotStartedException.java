package de.conciso.codingdojo;

public class GameNotStartedException extends RuntimeException {

    public GameNotStartedException(String reason) {
        super(reason);
    }
}
