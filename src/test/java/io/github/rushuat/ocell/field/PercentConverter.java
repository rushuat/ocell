package io.github.rushuat.ocell.field;

public class PercentConverter implements ValueConverter<String, Integer> {

  private static final String MARK = "%";

  @Override
  public String toModel(Integer value) {
    return value == null ? "" : value + MARK;
  }

  @Override
  public Integer toDocument(String value) {
    return value == null || value.isEmpty() ? null : Integer.valueOf(value.replaceAll(MARK, ""));
  }
}
