package de.conciso.codingdojo.reversi;

public record Player<T>(String name, Disc<T> disc) { }
