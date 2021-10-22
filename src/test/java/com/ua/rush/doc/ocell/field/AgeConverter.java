package com.ua.rush.doc.ocell.field;

public class AgeConverter implements ValueConverter<Integer, String> {

  private static final String TEXT = " year(s) old";

  @Override
  public Integer convertInput(String value) {
    Integer age = null;
    if (value != null) {
      age = Integer.valueOf(value.split(" ")[0]);
    }
    return age;
  }

  @Override
  public String convertOutput(Integer value) {
    String age = null;
    if (value != null) {
      age = value + TEXT;
    }
    return age;
  }
}
