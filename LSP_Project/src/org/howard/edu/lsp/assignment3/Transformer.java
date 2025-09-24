package org.howard.edu.lsp.assignment3;

/**
 * Transforms a single row from input columns to output columns.
 */
public interface Transformer {
    /**
     * Applies business rules to one data row.
     * @param cols input columns
     * @return output columns
     */
    String[] transformRow(String[] cols);
}
