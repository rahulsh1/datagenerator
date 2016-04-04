package org.generator;

import org.generator.sql.SQLDataGenerator;

/**
 * Factory for getting the output format generator.
 */
public final class DataGeneratorFactory {

  enum Type {
    SQL,
    JSON
  }

  public static DataGenerator getDataGenerator(final Type type, String schemaFile) {
    DataGenerator generator = null;
    if (type == Type.SQL) {
      generator = new SQLDataGenerator(schemaFile);
    } else {
      throw new IllegalArgumentException("Type: " + type + " not supported yet.");
    }
    return generator;
  }
}
