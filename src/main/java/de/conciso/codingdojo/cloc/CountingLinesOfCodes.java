package de.conciso.codingdojo.cloc;

import java.io.File;
import java.io.IOException;

/**
 * This interface must be implemented in this code kata. The task is to count all lines of java code of the file
 * given as parameter. The rules for the code are the following:
 * <ol>
 *     <li>Single line comments started with // must be excluded</li>
 *     <li>Block comments started with {@literal /}* and ended with *{@literal /} must be excluded.
 *     These comments must not be nested</li>
 *     <li>Empty lines must be excluded</li>
 *     <li>JavaDoc started with with {@literal /}** and ended with *{@literal /} must be excluded.
 *     JavaDoc must not be nested either</li>
 * </ol>
 */
public interface CountingLinesOfCodes {
    /**
     * This method count the lines of code of the given java file.
     * @param sourceCodeFile valid java file
     * @return Number of code lines of the file
     */
    int countLinesOfCode(File sourceCodeFile) throws IOException;
}
