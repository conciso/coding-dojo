package de.conciso.codingdojo.cloc;

public interface LineReader {
    /**
     * Zeilenweise einlesen
     * @return null, wenn das Ende der Datei erreicht wurde, sonst die nächste Zeile
     */
    String read();
}
