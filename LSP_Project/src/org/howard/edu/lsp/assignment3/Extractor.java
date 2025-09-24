package org.howard.edu.lsp.assignment3;

import java.io.IOException;

/**
 * Abstraction over a source of tabular rows.
 */
public interface Extractor {
    /**
     * Extracts data rows from the input.
     * @param inputFile path to the input source
     * @return ExtractResult containing rows, count, and header presence
     * @throws IOException if reading fails
     */
    ExtractResult extract(String inputFile) throws IOException;
}
