package de.conciso.codingdojo.cloc;

import java.io.*;
import java.util.Objects;

public class CountingLinesOfCodeImpl implements CountingLinesOfCodes {
    @Override
    public int countLinesOfCode(File sourceCodeFile) {

        Objects.requireNonNull(sourceCodeFile, "sourceCodeFile is null");

        if (!sourceCodeFile.isFile()) {
            throw new RuntimeException("'" + sourceCodeFile + "' does not exist or is not a file");
        }
        int numberOfLines = 0;
        try ( final BufferedReader bufferedReader = new BufferedReader( new FileReader( sourceCodeFile ) ) ){
            String line = bufferedReader.readLine();
            while( null != line )
            {
                line = stripBlockComments(line);
                line = stripLineComment(line);
                if(!line.trim().isEmpty()) {
                    numberOfLines++;
                }

                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        return numberOfLines;
    }

    private String stripBlockComments(final String line) {
        final int startOfBlockComment = line.indexOf("/*");

        return null;
    }

    private String stripLineComment(final String line) {
        final int startOfLineComment = line.indexOf("//");
        return startOfLineComment == -1 ? line : line.substring(0, startOfLineComment);
    }
}
