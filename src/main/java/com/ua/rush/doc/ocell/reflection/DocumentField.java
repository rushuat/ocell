package com.ua.rush.doc.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.rush.doc.ocell.annotation.BooleanValue;
import com.ua.rush.doc.ocell.annotation.DateValue;
import com.ua.rush.doc.ocell.annotation.FieldFormat;
import com.ua.rush.doc.ocell.annotation.FieldName;
import com.ua.rush.doc.ocell.annotation.FiledOrder;
import com.ua.rush.doc.ocell.annotation.NumberValue;
import com.ua.rush.doc.ocell.annotation.StringValue;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.SneakyThrows;

public class DocumentField {

  private Field field;

  public DocumentField(Field field) {
    this.field = field;
  }

  @SneakyThrows
  public <E, V> void setValue(E obj, V value) {
    Object data = value;
    if (data != null) {
      if (field.getType().equals(Float.class)) {
        data = ((Number) value).floatValue();
      } else if (field.getType().equals(Double.class)) {
        data = ((Number) value).doubleValue();
      } else if (field.getType().equals(Byte.class)) {
        data = ((Number) value).byteValue();
      } else if (field.getType().equals(Integer.class)) {
        data = ((Number) value).intValue();
      } else if (field.getType().equals(Short.class)) {
        data = ((Number) value).shortValue();
      } else if (field.getType().equals(Long.class)) {
        data = ((Number) value).longValue();
      }
    }

    field.setAccessible(true);
    field.set(obj, data);
    field.setAccessible(false);
  }

  @SneakyThrows
  public <E, V> V getValue(E obj) {
    field.setAccessible(true);
    V data = (V) field.get(obj);
    field.setAccessible(false);

    Object value = data;
    if (value == null) {
      if (field.isAnnotationPresent(StringValue.class)) {
        value = field.getAnnotation(StringValue.class).value();
      } else if (field.isAnnotationPresent(BooleanValue.class)) {
        value = field.getAnnotation(BooleanValue.class).value();
      } else if (field.isAnnotationPresent(NumberValue.class)) {
        value = field.getAnnotation(NumberValue.class).value();
      } else if (field.isAnnotationPresent(DateValue.class)) {
        String date = field.getAnnotation(DateValue.class).value();
        String format = getFormat();
        DateTimeFormatter formatter =
            format.isEmpty()
                ? DateTimeFormatter.ISO_ZONED_DATE_TIME
                : DateTimeFormatter.ofPattern(format);
        LocalDateTime local = LocalDateTime.from(formatter.parse(date));
        ZonedDateTime zoned = ZonedDateTime.of(local, ZoneId.systemDefault());
        value = Date.from(zoned.toInstant());
      }
    }
    return (V) value;
  }

  public String getFormat() {
    String format = null;
    if (field.isAnnotationPresent(JsonFormat.class)) {
      format = field.getAnnotation(JsonFormat.class).pattern();
    }
    if (field.isAnnotationPresent(FieldFormat.class)) {
      format = field.getAnnotation(FieldFormat.class).value();
    }
    if (format == null || format.isBlank()) {
      format = "";
    }
    return format;
  }

  public Integer getOrder() {
    return
        field.isAnnotationPresent(FiledOrder.class)
            ? field.getAnnotation(FiledOrder.class).value()
            : Integer.MAX_VALUE;
  }

  public String getName() {
    String name = null;
    if (field.isAnnotationPresent(JsonProperty.class)) {
      name = field.getAnnotation(JsonProperty.class).value();
    }
    if (field.isAnnotationPresent(FieldName.class)) {
      name = field.getAnnotation(FieldName.class).value();
    }
    if (name == null || name.isBlank()) {
      name = field.getName();
    }
    return name;
  }

  public Class<?> getType() {
    return field.getType();
  }
}
