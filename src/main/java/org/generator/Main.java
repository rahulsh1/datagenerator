package org.generator;

/**
 * Main Program.
 */
public final class Main {

  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("java Main <num_rows> <schema_file> <output_directory>");
      System.exit(0);
    }
    final int numRows = Integer.parseInt(args[0]);
    final String schemaFile = args[1];
    final String outputDirectory = args[2];

    try {
      final DataGenerator dataGenerator =
          DataGeneratorFactory.getDataGenerator(DataGeneratorFactory.Type.SQL, schemaFile);
//      dataGenerator.enableBulkMode();
      dataGenerator.generate(numRows, outputDirectory);
      System.out.println("Done");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
