package de.conciso.codingdojo.reversi;

import de.conciso.codingdojo.reversi.implementation.CharacterDisc;

public record Player<T>(String name, CharacterDisc disc) { }
