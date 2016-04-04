package org.generator.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public final class SchemaParser {

  private final CCJSqlParserManager parserManager;

  public SchemaParser() {
    parserManager = new CCJSqlParserManager();
  }

  public List<Statement> parseSchema(final String fileName) throws Exception {
    final String[] schemas = readSchemaFromFile(fileName);
    final List<Statement> statements = new ArrayList<>();
    for (String schema : schemas) {
      statements.add(parserManager.parse(new java.io.StringReader(schema)));
    }
    return statements;
  }

  private String[] readSchemaFromFile(final String schema) throws IOException {
    final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(schema)));
    final StringBuilder sql = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      // Ignore comments
      if (line.startsWith("--")) {
        continue;
      }
      sql.append(line);
    }
    br.close();
    final String data = sql.toString().replaceAll("\"", "");
    return data.split(";");
  }

  private void listColumns(final String schemaFile) throws Exception {
    final List<Statement> statements = parseSchema(schemaFile);
    for (Statement statement : statements) {
      if (statement instanceof CreateTable) {
        CreateTable create = (CreateTable) statement;
        String name = create.getTable().getName();

        System.out.println("\nTable: " + name);
        List<ColumnDefinition> columns = create.getColumnDefinitions();
        for (ColumnDefinition col : columns) {
          System.out.printf("%-15s - %-10s:%10s - %10s\n", col.getColumnName(), col.getColDataType().getDataType(),
              col.getColDataType().getArgumentsStringList(),col.getColumnSpecStrings());
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    String schemaFile = "./schema.sql";
    new SchemaParser().listColumns(schemaFile);
  }
}