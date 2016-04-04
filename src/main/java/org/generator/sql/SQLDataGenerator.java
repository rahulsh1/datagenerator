package org.generator.sql;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.generator.DataGenerator;
import org.generator.utils.RandomDataUtils;
import org.generator.utils.SchemaParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Generate data in SQL form.
 */
public final class SQLDataGenerator implements DataGenerator {

  private final SchemaParser schemaParser;
  private final RandomDataUtils randomDataUtils;
  private final String schemaFile;

  private boolean isBulk;
  private List<Statement> parsedStatements;

  public SQLDataGenerator(String schemaFile) {
    schemaParser = new SchemaParser();
    randomDataUtils = new RandomDataUtils();
    this.schemaFile = schemaFile;
  }

  @Override
  public void generate(int numRecords, String outputDirectory) throws Exception {

    // Parse the input schema
    parseSchema();

    BufferedWriter fileWriter;
    long startTimeMillis;
    long endTimeMillis;
    for (Statement statement : parsedStatements) {
      if (statement instanceof CreateTable) {
        final CreateTable createTable = (CreateTable) statement;
        final String tableName = createTable.getTable().getName();

        final String outputFile = outputDirectory + File.separator + tableName + ".dat";
        fileWriter = new BufferedWriter(new FileWriter(outputFile));

        System.out.println("Generating Records for " + tableName + " Bulk:" + isBulk);
        startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < numRecords; i++) {
          final String record;
          if (isBulk) {
            record = generateValues(createTable.getColumnDefinitions(), true);
          } else {
            record = generateInsertQuery(createTable);
          }
          fileWriter.write(record + "\r\n");
          // show progress
          if (i % 1000 == 0) System.out.print(".");
        }
        endTimeMillis = System.currentTimeMillis();
        System.out.println(".");
        System.out.println("Records generated for " + tableName + " in " + outputFile + " Records:" + numRecords +
            " - Time taken: " + (endTimeMillis - startTimeMillis)/1000.0 + " seconds.");

        fileWriter.flush();
        fileWriter.close();
      }

      // Reset counters and other counters for next table.
      randomDataUtils.resetCache();
    }
  }

  @Override
  public void enableBulkMode() {
    isBulk = true;
  }

  private void parseSchema() throws Exception {
    parsedStatements = schemaParser.parseSchema(schemaFile);
  }

  private String createColumnString(List<ColumnDefinition> columnDefinitions) {
    // TODO - can be cached.
    final StringBuilder cols = new StringBuilder();
    cols.append("(");
    int i = 1;
    for (ColumnDefinition column : columnDefinitions) {
      String name = column.getColumnName();
      cols.append(name);
      if (i < columnDefinitions.size()) {
        cols.append(", ");
      }
      i++;
    }
    cols.append(")");
    return cols.toString();
  }

  private String generateValues(List<ColumnDefinition> columnDefinitions, boolean isBulk) {
    final StringBuilder values = new StringBuilder();
    int i = 1;
    for (ColumnDefinition column : columnDefinitions) {
      final String type = column.getColDataType().getDataType();
      final List specs = column.getColumnSpecStrings();
      final String regex = specs != null ? (String) specs.get(0) : null;

      final String value = randomDataUtils.getRandom(column.getColumnName(), type, 0, regex);
      if (isNotString(type) || isBulk) {
        values.append(value);
      } else {
        values.append("'").append(value).append("'");
      }
      if (i < columnDefinitions.size()) {
        if (isBulk) {
          values.append(",");
        } else {
          values.append(", ");
        }
      }
      i++;
    }
    if (isBulk) {
      values.append(",");
    }
    return values.toString();
  }

  private String generateInsertQuery(CreateTable table) {
    final StringBuilder buffer = new StringBuilder();
    buffer.append("INSERT INTO ");
    buffer.append(table.getTable().getName());
    buffer.append(" ");
    buffer.append(createColumnString(table.getColumnDefinitions()));
    buffer.append(" VALUES (");
    buffer.append(generateValues(table.getColumnDefinitions(), false));
    buffer.append(" );");

    return buffer.toString();
  }

  private boolean isNotString(String type) {
    return type.equalsIgnoreCase("NUMBER");
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      System.out.println("java SQLDataGenerator <num_rows> <schema_file> <output_directory>");
      System.exit(0);
    }
    int numRows = Integer.parseInt(args[0]);
    String schemaFile = args[1];
    String outputDirectory = args[2];

    SQLDataGenerator dataGenerator = new SQLDataGenerator(schemaFile);
    dataGenerator.generate(numRows, outputDirectory);
    System.out.println("Done");
  }
}
