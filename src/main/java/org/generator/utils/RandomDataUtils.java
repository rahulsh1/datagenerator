package org.generator.utils;

import com.mifmif.common.regex.Generex;
import org.generator.DataTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates random data based on the given regex if overriden in the schema file or defaults to known types.
 */
public final class RandomDataUtils {

  private final static String RUNNING_NUM_TYPE = "##R";
  private Generex TIMESTAMP_REGEX = new Generex(DataTypes.TIMESTAMP_REGEX);
  private Generex NUMBER_REGEX = new Generex(DataTypes.NUMBER_REGEX);
  private Generex VARCHAR_REGEX = new Generex(DataTypes.VARCHAR_REGEX);
  private Generex BOOLEAN_REGEX = new Generex(DataTypes.BOOLEAN_REGEX);

  final Map<String, Generex> generexCache;
  final long counters[];
  final Map<String, Integer> countersMap;
  private int COUNTER;

  public RandomDataUtils() {
    generexCache = new HashMap<>();
    countersMap = new HashMap<>();
    counters = new long[5];
  }

  public String getRandom(String name, String type, int length, String regex) {
    String result = "";

    if (regex != null) {
      if (isSpecial(regex)) {
        result = String.valueOf(getSpecialValue(name, regex));
      } else {
        Generex generex = generexCache.get(regex);
        if (generex == null) {
          generex = new Generex(cleanRegex(regex));
          generexCache.put(regex, generex);
        }
        result = generex.random();
      }
    } else {
      switch (type) {
        case "NUMBER":
          result = NUMBER_REGEX.random();
          break;
        case "TIMESTAMP":
          result = TIMESTAMP_REGEX.random();
          break;
        case "VARCHAR2":
          result = VARCHAR_REGEX.random();
          break;
        case "CHAR":
          result = BOOLEAN_REGEX.random();
          break;
        default:
          System.out.println(type + " is not supported. FIX ME!!");
      }
    }
    return result;
  }

  public void resetCache() {
    generexCache.clear();
    countersMap.clear();
    for (int i = 0; i < counters.length; i++) {
      counters[i] = 0L;
    }
    COUNTER = 0;
  }
  private boolean isSpecial(String regex) {
    return regex.startsWith("'##");
  }

  private long getSpecialValue(String name, String regex) {
    Integer index = countersMap.get(name);
    final int idx;
    if (index == null) {
      countersMap.put(name, COUNTER);
      idx = COUNTER;
      COUNTER++;
    } else {
      idx = index;
    }
    return ++counters[idx];
  }

  private String cleanRegex(String regex) {
    return regex.replaceAll("'", "");
  }

  private void test() {
    Generex generexTS = new Generex(DataTypes.TIMESTAMP_REGEX);
    Generex generexNUM = new Generex(DataTypes.NUMBER_REGEX);
    Generex generexVAR = new Generex(DataTypes.VARCHAR_REGEX);
    Generex generexBOOL = new Generex(DataTypes.BOOLEAN_REGEX);

    for (int i = 0; i < 10; i++) {
      System.out.printf("%d - %-25s - %10s - %20s - %5s\n", i, generexTS.random(), generexNUM.random(),
          generexVAR.random() ,generexBOOL.random());
    }

  }

  private void specialValueTest() {
    for (int i = 0; i < 10; i++) {
      System.out.println("Special value: " + getRandom("ID", "NUMBER", 0, "'##R'") +
          " - " + getRandom("T_ID", "NUMBER", 0, "'##R'"));
    }
  }

  public static void main(String[] args) {
    RandomDataUtils generator = new RandomDataUtils();
    generator.test();
    generator.specialValueTest();
  }

}
