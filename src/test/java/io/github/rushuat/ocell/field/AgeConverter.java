package io.github.rushuat.ocell.field;

public class AgeConverter implements ValueConverter<Integer, String> {

  private static final String SUFFIX = " year(s) old";

  @Override
  public Integer toModel(String value) {
    return value == null || value.isEmpty() ? null : Integer.valueOf(value.split(" ")[0]);
  }

  @Override
  public String toDocument(Integer value) {
    return value == null ? "" : value + SUFFIX;
  }
}
