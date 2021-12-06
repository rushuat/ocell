package io.github.rushuat.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.FieldAlignment;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldExclude;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldName;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.field.ValueConverter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Transient;
import lombok.SneakyThrows;

public class DocumentField {

  private Field field;
  private Map<Class<? extends ValueConverter>, ValueConverter> converterCache;

  public DocumentField(
      Field field,
      Map<Class<? extends ValueConverter>, ValueConverter> converterCache) {
    this.field = field;
    this.field.setAccessible(true);
    this.converterCache = converterCache;
  }

  @SneakyThrows
  public void setValue(Object obj, Object value) {
    Object data = value;
    ValueConverter converter = getConverter();
    if (converter != null) {
      data = converter.convertInput(data);
    } else {
      if (data instanceof Number) {
        data = getNumber(data);
      }
    }
    if (data == null) {
      data = getDefault();
    }
    field.set(obj, data);
  }

  @SneakyThrows
  public Object getValue(Object obj) {
    Object value = field.get(obj);
    if (value == null) {
      value = getDefault();
    }
    ValueConverter converter = getConverter();
    if (converter != null) {
      value = converter.convertOutput(value);
    }
    return value;
  }

  public Object getNumber(Object value) {
    Object data = value;
    if (field.getType().equals(Double.class)) {
      data = ((Number) value).doubleValue();
    } else if (field.getType().equals(Integer.class)) {
      data = ((Number) value).intValue();
    } else if (field.getType().equals(Long.class)) {
      data = ((Number) value).longValue();
    } else if (field.getType().equals(Float.class)) {
      data = ((Number) value).floatValue();
    } else if (field.getType().equals(Byte.class)) {
      data = ((Number) value).byteValue();
    } else if (field.getType().equals(Short.class)) {
      data = ((Number) value).shortValue();
    }
    return data;
  }

  @SneakyThrows
  public Object getDefault() {
    Object value = null;
    if (field.isAnnotationPresent(StringValue.class)) {
      value = field.getAnnotation(StringValue.class).value();
    } else if (field.isAnnotationPresent(BooleanValue.class)) {
      value = field.getAnnotation(BooleanValue.class).value();
    } else if (field.isAnnotationPresent(NumberValue.class)) {
      Double number = field.getAnnotation(NumberValue.class).value();
      value = getNumber(number);
    } else if (field.isAnnotationPresent(DateValue.class)) {
      String date = field.getAnnotation(DateValue.class).value();
      Format format = getFormat();
      DateFormat formatter =
          format.getPattern() == null
              ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
              : new SimpleDateFormat(format.getPattern());
      value = formatter.parse(date);
    }
    return value;
  }

  public Format getFormat() {
    String pattern = null;
    if (field.isAnnotationPresent(JsonFormat.class)) {
      JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
      if (!jsonFormat.pattern().isBlank()) {
        pattern = jsonFormat.pattern();
      }
    }
    if (field.isAnnotationPresent(FieldFormat.class)) {
      FieldFormat fieldFormat = field.getAnnotation(FieldFormat.class);
      if (!fieldFormat.value().isBlank()) {
        pattern = fieldFormat.value();
      }
    }
    boolean isDate = getType().equals(Date.class);
    return new Format(pattern, isDate);
  }

  public Alignment getAlignment() {
    Alignment alignment = new Alignment();
    if (field.isAnnotationPresent(FieldAlignment.class)) {
      FieldAlignment fieldAlignment = field.getAnnotation(FieldAlignment.class);
      if (!fieldAlignment.horizontal().isBlank()) {
        alignment.setHorizontal(fieldAlignment.horizontal().toUpperCase());
      }
      if (!fieldAlignment.vertical().isBlank()) {
        alignment.setVertical(fieldAlignment.vertical().toUpperCase());
      }
    }
    return alignment;
  }

  public Integer getOrder() {
    int order = -1;
    if (field.isAnnotationPresent(JsonProperty.class)) {
      order = field.getAnnotation(JsonProperty.class).index();
    }
    if (field.isAnnotationPresent(FieldOrder.class)) {
      order = field.getAnnotation(FieldOrder.class).value();
    }
    return order < 0 ? Integer.MAX_VALUE : order;
  }

  public boolean isExcluded() {
    boolean excluded = false;
    if (field.isAnnotationPresent(Transient.class)) {
      excluded = true;
    }
    if (field.isAnnotationPresent(JsonIgnore.class)) {
      excluded = field.getAnnotation(JsonIgnore.class).value();
    }
    if (field.isAnnotationPresent(FieldExclude.class)) {
      excluded = field.getAnnotation(FieldExclude.class).value();
    }
    return excluded;
  }

  public String getName() {
    String name = null;
    if (field.isAnnotationPresent(Column.class)) {
      name = field.getAnnotation(Column.class).name();
    }
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

  public ValueConverter getConverter() {
    ValueConverter converter = null;
    if (field.isAnnotationPresent(FieldConverter.class)) {
      Class<? extends ValueConverter> clazz = field.getAnnotation(FieldConverter.class).value();
      converter = converterCache.computeIfAbsent(clazz, this::newConverter);
    }
    return converter;
  }

  @SneakyThrows
  private ValueConverter newConverter(Class<? extends ValueConverter> clazz) {
    return clazz.getDeclaredConstructor().newInstance();
  }
}
