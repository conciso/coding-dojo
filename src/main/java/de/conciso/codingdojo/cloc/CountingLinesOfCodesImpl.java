package de.conciso.codingdojo.cloc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CountingLinesOfCodesImpl implements CountingLinesOfCodes {
    @Override
    public int countLinesOfCode(File sourceCodeFile) throws IOException {
         List<String> lines = Files.readAllLines(sourceCodeFile.toPath());

         return (int) lines.stream()
                 .map(line -> line.trim())
                 .filter(line -> !line.isEmpty())
                 .filter(line -> !line.startsWith("//"))
                 .count();
    }

    public String filterLine(final String input)
    {
        return input;
    }
}
