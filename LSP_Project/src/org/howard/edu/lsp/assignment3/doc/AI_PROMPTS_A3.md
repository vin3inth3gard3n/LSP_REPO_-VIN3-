# AI Prompts & Excerpts (Assignment 3)

> You do **not** need full transcripts—just representative prompts + short excerpts.

## Prompt 1
**My prompt:**  
“Refactor my single-class Java ETL (extract/transform/load) into an object-oriented design that preserves behavior and relative file paths. I need interfaces for extractor, transformer, loader, and a pipeline orchestrator.”

**AI excerpt:**  
“Create `Extractor`, `Transformer`, and `Loader` interfaces and concrete `CsvExtractor`, `ProductTransformer`, `CsvLoader`. Pass them into a `Pipeline` that coordinates ETL. Keep the same header and summary format to match A2.”

**My adaptation/notes:**  
I kept the exact A2 messages and rounding behavior. I added `Rounder` and `RunSummary` as utilities.

## Prompt 2
**My prompt:**  
“Ensure empty-input (header only) still writes a header-only output and prints a warning, and missing input prints the same A2 error.”

**AI excerpt:**  
“Check `hadHeader` in the extract result; if there are zero rows and `hadHeader` is true, write just the header and return with a warning.”

**My adaptation/notes:**  
Implemented `ExtractResult.hadHeader()` and early return. I matched the warning text exactly.

## Prompt 3
**My prompt:**  
“Generate Javadocs for public classes/methods and ensure one public class per file under `org.howard.edu.lsp.assignment3`.”

**AI excerpt:**  
“Add brief class descriptions and method parameter docs. Keep one public class per file and correct package.”

**My adaptation/notes:**  
Reviewed to make sure the comments reflect the real logic, especially the price range and Electronics discount rules.
