package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/**
 * Abstraction for writing transformed rows to an output.
 */
public interface Loader {
    /**
     * Writes the header and transformed rows to the output target.
     * @param outputFile path to the output
     * @param transformedRows transformed data rows
     * @throws IOException if writing fails
     */
    void load(String outputFile, List<String[]> transformedRows) throws IOException;
}
