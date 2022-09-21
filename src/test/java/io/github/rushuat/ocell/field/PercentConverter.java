package io.github.rushuat.ocell.field;

public class PercentConverter implements ValueConverter<String, Integer> {

  private static final String TEXT = "%";

  @Override
  public String convertInput(Integer value) {
    String percent = null;
    if (value != null) {
      percent = value + TEXT;
    }
    return percent;
  }

  @Override
  public Integer convertOutput(String value) {
    Integer percent = null;
    if (value != null) {
      percent = Integer.valueOf(value.replaceAll(TEXT, ""));
    }
    return percent;
  }
}
