package io.github.rushuat.ocell.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements ValueConverter<String, Date> {

  private static final String PATTERN = "yyyyMMdd";

  @Override
  public String toModel(Date value) {
    return value == null ? null : new SimpleDateFormat(PATTERN).format(value);
  }

  @Override
  public Date toDocument(String value) throws ParseException {
    return value == null || value.isEmpty() ? null : new SimpleDateFormat(PATTERN).parse(value);
  }
}
