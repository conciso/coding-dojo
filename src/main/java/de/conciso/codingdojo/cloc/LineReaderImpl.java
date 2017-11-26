package de.conciso.codingdojo.cloc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class LineReaderImpl implements LineReader {

    private final LineNumberReader reader;

    public LineReaderImpl() throws FileNotFoundException {

            reader = new LineNumberReader(new FileReader("TestOneLine.java"));

    }

    @Override
    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
