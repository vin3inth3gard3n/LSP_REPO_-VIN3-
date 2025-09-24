package org.howard.edu.lsp.assignment3;

import java.util.List;

/**
 * Immutable container for the extractor output.
 */
public class ExtractResult {
    private final List<String[]> rows;
    private final int rowsRead; 
    private final boolean hadHeader;

    /**
     * @param rows data rows (excluding the header)
     * @param rowsRead count of data rows read
     * @param hadHeader whether a header line was present
     */
    public ExtractResult(final List<String[]> rows, final int rowsRead, final boolean hadHeader) {
        this.rows = rows;
        this.rowsRead = rowsRead;
        this.hadHeader = hadHeader;
    }

    public List<String[]> getRows() { return rows; }

    public int getRowsRead() { return rowsRead; }

    public boolean hadHeader() { return hadHeader; }
}
