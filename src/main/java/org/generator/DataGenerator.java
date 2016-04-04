package org.generator;

/**
 * Common interface for all data generators.
 */
public interface DataGenerator {

  /**
   * Generates records in the given output directory. One file will be generated for each table in schema.sql.
   * @param records number of records
   * @param outputDirectory directory where to generate the files
   *
   * @throws Exception - If parsing the schema fails or any other errors with data generation.
   */
  void generate(int records, String outputDirectory) throws Exception;

  /**
   * Enables bulk mode inserts if supported.
   */
  void enableBulkMode();
}
