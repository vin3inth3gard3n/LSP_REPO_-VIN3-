package org.howard.edu.lsp.assignment3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Orchestrates the Extract → Transform → Load sequence.
 * Preserves A2's summary print format and error behavior.
 */
public class Pipeline {
    private final Extractor extractor;
    private final Transformer transformer;
    private final Loader loader;

    /**
     * Constructs a Pipeline with pluggable components.
     * @param extractor component that reads input rows
     * @param transformer component that transforms each row
     * @param loader component that writes output rows
     */
    public Pipeline(final Extractor extractor, final Transformer transformer, final Loader loader) {
        this.extractor = extractor;
        this.transformer = transformer;
        this.loader = loader;
    }

    /**
     * Runs the ETL and prints a run summary (same format as A2).
     * @param inputFile relative path to input CSV (e.g., data/products.csv)
     * @param outputFile relative path for output CSV (e.g., data/transformed_products.csv)
     */
    public void runETL(final String inputFile, final String outputFile) {
        int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;

        try {
            // ---- Extract ----
            ExtractResult extractResult = extractor.extract(inputFile);
            rowsRead = extractResult.getRowsRead();

            // Empty file (header only): still create output with just header
            if (extractResult.getRows().isEmpty() && extractResult.hadHeader()) {
                loader.load(outputFile, Collections.emptyList());
                System.out.println("⚠️ Input file empty. Output file created with header only.");
                RunSummary.print(rowsRead, rowsTransformed, rowsSkipped, outputFile);
                return;
            }

            // ---- Transform ----
            List<String[]> transformed = new ArrayList<>();
            for (String[] cols : extractResult.getRows()) {
                try {
                    transformed.add(transformer.transformRow(cols));
                    rowsTransformed++;
                } catch (Exception ex) {
                    // Skip malformed rows (don't crash)
                    rowsSkipped++;
                }
            }

            // ---- Load ----
            loader.load(outputFile, transformed);
            System.out.println(" Transformation complete. File saved to: " + outputFile);

        } catch (FileNotFoundException e) {
            System.out.println(" ERROR: Input file not found at " + inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            RunSummary.print(rowsRead, rowsTransformed, rowsSkipped, outputFile);
        }
    }
}
