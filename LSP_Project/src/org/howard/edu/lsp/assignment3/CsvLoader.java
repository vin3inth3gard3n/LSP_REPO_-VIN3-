package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * CSV loader that writes the Assignment 2 header and rows.
 */
public class CsvLoader implements Loader {
    @Override
    public void load(final String outputFile, final List<String[]> transformedRows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            // Always write header exactly as A2
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();

            for (String[] row : transformedRows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}
