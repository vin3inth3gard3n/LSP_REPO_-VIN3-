# Assignment 3 Reflection (A2 vs A3)

**What changed in the design?**  
A2 used a single class (`ETLPipeline`) with static methods to extract, transform, and load. A3 decomposes responsibilities into separate classes and interfaces: `Extractor`/`CsvExtractor`, `Transformer`/`ProductTransformer`, `Loader`/`CsvLoader`, orchestrated by `Pipeline` and launched by `ETLPipelineApp`. Utility concerns (`Rounder`, `RunSummary`) are factored out.

**How is A3 more object-oriented?**  
- **Abstraction & Encapsulation:** Each component hides its implementation behind an interface (`Extractor`, `Transformer`, `Loader`).  
- **Single Responsibility:** Each class has one clear job (read, transform, write, coordinate).  
- **Open/Closed Principle:** I can add another `Transformer` or `Extractor` without changing `Pipeline`.  
- **Dependency Injection:** `Pipeline` receives its collaborators at construction time, enabling test doubles.

**OO Concepts Used**
- **Object/Class:** Concrete classes implement behavior (`CsvExtractor`, `ProductTransformer`, `CsvLoader`).  
- **Encapsulation:** Internal details (e.g., CSV parsing, rounding) are private to components.  
- **Inheritance/Polymorphism:** Via interfaces; `Pipeline` calls `Extractor/Transformer/Loader` polymorphically. I could subclass a base transformer to override rules, or add alternatives like `JsonExtractor`.  
- **Composition:** `Pipeline` composes the three roles to deliver the full ETL.

**Testing Equivalence with A2**  
1. Reused the same `data/products.csv`.  
2. Diffed `data/transformed_products.csv` from A2 and A3; they match byte-for-byte for valid inputs.  
3. Verified “missing file” prints `ERROR: Input file not found at ...` and shows the same run summary format.  
4. Verified “header only” input creates an output with only the header and prints the same warning message.  
5. Verified malformed rows are skipped (counted) without crashing.

**Tradeoffs / Lessons**  
- A3 adds more files but makes each concern easier to test and swap.  
- Interfaces + small classes made it simple to mock parts and assert behaviors.  
- Clear package + Javadocs improved readability and grading alignment.
