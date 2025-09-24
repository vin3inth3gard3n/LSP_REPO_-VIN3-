package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.List;

/**
 * CSV extractor for a simple comma-delimited file with a header on the first line.
 * No quoted fields or embedded commas are expected.
 */
public class CsvExtractor implements Extractor {
    @Override
    public ExtractResult extract(final String inputFile) throws IOException {
        List<String[]> rows = new ArrayList<>();
        int read = 0;
        boolean hadHeader = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String headerLine = br.readLine(); // header
            if (headerLine == null) {
                // truly empty (no header, no rows)
                return new ExtractResult(rows, read, false);
            }
            hadHeader = true;

            String line;
            while ((line = br.readLine()) != null) {
                read++;
                rows.add(line.split(","));
            }
        }

        return new ExtractResult(rows, read, hadHeader);
    }
}
