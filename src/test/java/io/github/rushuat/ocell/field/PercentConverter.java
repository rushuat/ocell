package io.github.rushuat.ocell.field;

public class PercentConverter implements ValueConverter<String, Number> {

  private static final String TEXT = "%";

  @Override
  public String convertInput(Number value) {
    String percent = null;
    if (value != null) {
      percent = value.intValue() + TEXT;
    }
    return percent;
  }

  @Override
  public Number convertOutput(String value) {
    Number percent = null;
    if (value != null) {
      percent = Integer.valueOf(value.replaceAll(TEXT, ""));
    }
    return percent;
  }
}
