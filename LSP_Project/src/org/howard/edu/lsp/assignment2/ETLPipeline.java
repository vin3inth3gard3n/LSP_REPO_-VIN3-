package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ETLPipeline {

    public static void main(String[] args) {
        // REQUIRED: read/write under project-root /data (NOT inside src/)
        String inputFile = "data/products.csv";
        String outputFile = "data/transformed_products.csv";
        runETL(inputFile, outputFile);
    }

    /** Orchestrates extract -> transform -> load and prints a run summary. */
    public static void runETL(String inputFile, String outputFile) {
        int rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;

        try {
            // ---- Extract ----
            ExtractResult extractResult = extract(inputFile);
            rowsRead = extractResult.rowsRead;

            // Handle the “empty file” case (header only): still create output with just header
            if (extractResult.rows.isEmpty() && extractResult.hadHeader) {
                load(outputFile, Collections.emptyList());
                System.out.println("⚠️ Input file empty. Output file created with header only.");
                printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFile);
                return;
            }

            // ---- Transform ----
            List<String[]> transformed = new ArrayList<>();
            for (String[] cols : extractResult.rows) {
                try {
                    transformed.add(transformRow(cols));
                    rowsTransformed++;
                } catch (Exception ex) {
                    // Any malformed row is skipped (counted but does not crash)
                    rowsSkipped++;
                }
            }

            // ---- Load ----
            load(outputFile, transformed);

            System.out.println(" Transformation complete. File saved to: " + outputFile);
            printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFile);

        } catch (FileNotFoundException e) {
            System.out.println(" ERROR: Input file not found at " + inputFile);
            printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
            printSummary(rowsRead, rowsTransformed, rowsSkipped, outputFile);
        }
    }

    /** Container for extract results. */
    private static class ExtractResult {
        final List<String[]> rows;
        final int rowsRead;     // number of data rows encountered (excludes header)
        final boolean hadHeader;

        ExtractResult(List<String[]> rows, int rowsRead, boolean hadHeader) {
            this.rows = rows;
            this.rowsRead = rowsRead;
            this.hadHeader = hadHeader;
        }
    }

    /** Extract: read CSV from data/products.csv (comma-delimited, header in first row). */
    private static ExtractResult extract(String inputFile) throws IOException {
        List<String[]> rows = new ArrayList<>();
        int read = 0;
        boolean hadHeader = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String headerLine = br.readLine(); // header
            if (headerLine == null) {
                // truly empty file (no header, no rows)
                return new ExtractResult(rows, read, false);
            }
            hadHeader = true;

            String line;
            while ((line = br.readLine()) != null) {
                read++;
                // Split by comma; spec says no quoted fields / commas inside fields
                String[] cols = line.split(",");
                rows.add(cols);
            }
        }
        return new ExtractResult(rows, read, hadHeader);
    }

    /** Transform a single data row according to the required order. */
    private static String[] transformRow(String[] cols) {
        if (cols.length < 4) throw new IllegalArgumentException("Row has fewer than 4 columns");

        int productId = Integer.parseInt(cols[0].trim());
        String name = cols[1].trim().toUpperCase();   // (1) uppercase name
        double price = Double.parseDouble(cols[2].trim());
        String category = cols[3].trim();

        // (2) discount if Electronics, round to 2 decimals (half up)
        if (category.equalsIgnoreCase("Electronics")) {
            price = round(price * 0.9, 2);
            // (3) recategorize if post-discount price > 500 and original category was Electronics
            if (price > 500.00) {
                category = "Premium Electronics";
            }
        }

        // Ensure final price is rounded to 2 decimals
        price = round(price, 2);

        // (4) PriceRange from final price
        final String priceRange =
                (price <= 10.00) ? "Low" :
                (price <= 100.00) ? "Medium" :
                (price <= 500.00) ? "High" : "Premium";

        return new String[] {
                String.valueOf(productId),
                name,
                String.format(java.util.Locale.ROOT, "%.2f", price),
                category,
                priceRange
        };
    }

    /** Load: write header + transformed rows to data/transformed_products.csv */
    private static void load(String outputFile, List<String[]> transformedRows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            // Always write header
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();

            // Then the rows
            for (String[] row : transformedRows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }

    /** Round half up to given decimals using BigDecimal. */
    private static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /** Print summary as required. */
    private static void printSummary(int read, int transformed, int skipped, String outputFile) {
        System.out.println("   Run Summary:");
        System.out.println("   Rows read: " + read);
        System.out.println("   Rows transformed: " + transformed);
        System.out.println("   Rows skipped: " + skipped);
        System.out.println("   Output path: " + outputFile);
    }
}
