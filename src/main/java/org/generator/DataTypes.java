package org.generator;

/**
 * Default out-of-box regex for the known types.
 * TODO - Add support to override these and take in new ones.
 */
public final class DataTypes {

  public final static String TIMESTAMP_REGEX = "((0[1-9])|(1[1-2]))-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)-201\\d{1} ((0[1-9])|(1[1-2])):((0[1-9])|([1-5][0-9])):((0[1-9])|([1-5][0-9])) ((AM)|(PM)) -07:00";
  public final static String NUMBER_REGEX = "\\d{4,9}";
  public final static String VARCHAR_REGEX = "[a-z]{1,20}[A-Z]{1,20}";
  public final static String BOOLEAN_REGEX = "Y|N{1,1}";
  public final static String CHAR_REGEX = "\\w{1,1}";
  public final static String DATE_REGEX = "((0[1-9])|(1[1-2]))-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)-201\\d{1}";

}
