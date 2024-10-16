package io.github.rushuat.ocell.field;

public class EmptyConverter implements ValueConverter<String, String> {

  @Override
  public String toModel(String value) {
    return value == null || value.isEmpty() ? null : value;
  }

  @Override
  public String toDocument(String value) {
    return value == null ? "" : value;
  }
}
