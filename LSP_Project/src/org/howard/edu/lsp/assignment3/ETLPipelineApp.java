package org.howard.edu.lsp.assignment3;

/**
 * Entry point for Assignment 3. 
 * Keeps A2's relative paths and overall behavior intact.
 */
public class ETLPipelineApp {
    public static void main(String[] args) {
        final String inputFile = "data/products.csv";
        final String outputFile = "data/transformed_products.csv";

        Extractor extractor = new CsvExtractor();
        Transformer transformer = new ProductTransformer();
        Loader loader = new CsvLoader();
        Pipeline pipeline = new Pipeline(extractor, transformer, loader);

        pipeline.runETL(inputFile, outputFile);
    }
}
