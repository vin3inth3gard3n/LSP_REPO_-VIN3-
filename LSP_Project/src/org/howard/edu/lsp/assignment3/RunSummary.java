package org.howard.edu.lsp.assignment3;

/**
 * Prints the summary in the exact format required by Assignment 2.
 */
public final class RunSummary {
    private RunSummary() {}

    /**
     * Prints the run summary lines.
     * @param read rows read (data rows)
     * @param transformed rows successfully transformed
     * @param skipped rows skipped due to errors
     * @param outputFile path where the output was written
     */
    public static void print(final int read, final int transformed, final int skipped, final String outputFile) {
        System.out.println("   Run Summary:");
        System.out.println("   Rows read: " + read);
        System.out.println("   Rows transformed: " + transformed);
        System.out.println("   Rows skipped: " + skipped);
        System.out.println("   Output path: " + outputFile);
    }
}
