package io.github.rushuat.ocell.field;

public class EmptyConverter implements ValueConverter<String, String> {

  @Override
  public String convertInput(String value) {
    return value == null || value.isEmpty() ? null : value;
  }

  @Override
  public String convertOutput(String value) {
    return value == null ? "" : value;
  }
}
