package io.github.rushuat.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.CharValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.EnumValue;
import io.github.rushuat.ocell.annotation.FieldAlignment;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldExclude;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldFormula;
import io.github.rushuat.ocell.annotation.FieldName;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.HeaderAlignment;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.field.ValueConverter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.SneakyThrows;

public class DocumentField {

  private final Field field;
  private final Map<Class<?>, ValueConverter> typeConverters;
  private final Map<Class<? extends ValueConverter>, ValueConverter> fieldConverters;

  public DocumentField(
      Field field,
      Map<Class<?>, ValueConverter> typeConverters,
      Map<Class<? extends ValueConverter>, ValueConverter> fieldConverters) {
    this.field = field;
    this.field.setAccessible(true);
    this.typeConverters = typeConverters;
    this.fieldConverters = fieldConverters;
  }

  @SneakyThrows
  public void setValue(Object obj, Object value) {
    Object data = value;
    Class<?> type =
        Optional.ofNullable(getSubtype())
            .filter(sub -> !sub.equals(getType()))
            .orElseGet(() -> (Class) getType());
    data =
        Optional.ofNullable(getEnum(data, type))
            .orElse(data);
    data =
        Optional.ofNullable(getNumber(data, type))
            .orElse(data);
    ValueConverter converter =
        Optional.ofNullable(getConverter())
            .orElseGet(() -> typeConverters.get(type));
    if (converter != null) {
      data = converter.convertInput(data);
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
    ValueConverter converter =
        Optional.ofNullable(getConverter())
            .orElseGet(() -> typeConverters.get(getType()));
    if (converter != null) {
      value = converter.convertOutput(value);
    }
    if (value instanceof Enum) {
      value = ((Enum<?>) value).name();
    }
    return value;
  }

  public Object getEnum(Object value, Class<?> type) {
    Object data = null;
    if (value instanceof String) {
      if (type.isEnum()) {
        for (Object item : type.getEnumConstants()) {
          if (((Enum<?>) item).name().equals(value)) {
            data = item;
          }
        }
      }
    }
    return data;
  }

  public Object getNumber(Object value, Class<?> type) {
    Object data = null;
    if (value instanceof Number) {
      if (type.equals(double.class) || type.equals(Double.class)) {
        data = ((Number) value).doubleValue();
      } else if (type.equals(int.class) || type.equals(Integer.class)) {
        data = ((Number) value).intValue();
      } else if (type.equals(long.class) || type.equals(Long.class)) {
        data = ((Number) value).longValue();
      } else if (type.equals(float.class) || type.equals(Float.class)) {
        data = ((Number) value).floatValue();
      } else if (type.equals(byte.class) || type.equals(Byte.class)) {
        data = ((Number) value).byteValue();
      } else if (type.equals(short.class) || type.equals(Short.class)) {
        data = ((Number) value).shortValue();
      }
    }
    return data;
  }

  @SneakyThrows
  public Object getDefault() {
    Object value = null;
    if (field.isAnnotationPresent(BooleanValue.class)) {
      value = field.getAnnotation(BooleanValue.class).value();
    } else if (field.isAnnotationPresent(StringValue.class)) {
      value = field.getAnnotation(StringValue.class).value();
    } else if (field.isAnnotationPresent(CharValue.class)) {
      value = field.getAnnotation(CharValue.class).value();
    } else if (field.isAnnotationPresent(NumberValue.class)) {
      Double number = field.getAnnotation(NumberValue.class).value();
      value = getNumber(number, getType());
    } else if (field.isAnnotationPresent(EnumValue.class)) {
      String name = field.getAnnotation(EnumValue.class).value();
      value = getEnum(name, getType());
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
    Class<?> type = getType();
    boolean isDate = type.equals(Date.class);
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

  public Alignment getHeader() {
    Alignment alignment = new Alignment();
    if (field.isAnnotationPresent(HeaderAlignment.class)) {
      HeaderAlignment headerAlignment = field.getAnnotation(HeaderAlignment.class);
      if (!headerAlignment.horizontal().isBlank()) {
        alignment.setHorizontal(headerAlignment.horizontal().toUpperCase());
      }
      if (!headerAlignment.vertical().isBlank()) {
        alignment.setVertical(headerAlignment.vertical().toUpperCase());
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

  public boolean isFormula() {
    boolean formula = false;
    if (field.isAnnotationPresent(FieldFormula.class)) {
      formula = field.getAnnotation(FieldFormula.class).value();
    }
    return formula;
  }

  public boolean isExcluded() {
    boolean excluded = false;
    if (Modifier.isStatic(field.getModifiers())) {
      excluded = true;
    }
    if (Modifier.isTransient(field.getModifiers())) {
      excluded = true;
    }
    if (field.isAnnotationPresent(Transient.class)) {
      excluded = true;
    }
    if (field.isAnnotationPresent(XmlTransient.class)) {
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
    if (field.isAnnotationPresent(XmlAttribute.class)) {
      name = field.getAnnotation(XmlAttribute.class).name();
    }
    if (field.isAnnotationPresent(XmlElement.class)) {
      name = field.getAnnotation(XmlElement.class).name();
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

  public Class<?> getSubtype() {
    Class<?> type = null;
    ValueConverter converter = getConverter();
    if (converter != null) {
      Class<?> clazz = converter.getClass();
      ParameterizedType generic = (ParameterizedType) clazz.getGenericInterfaces()[0];
      type = (Class<?>) generic.getActualTypeArguments()[1];
    }
    return type;
  }

  public ValueConverter getConverter() {
    ValueConverter converter = null;
    if (field.isAnnotationPresent(FieldConverter.class)) {
      Class<? extends ValueConverter> clazz =
          field.getAnnotation(FieldConverter.class).value();
      converter = fieldConverters.computeIfAbsent(clazz, this::newConverter);
    }
    return converter;
  }

  @SneakyThrows
  private ValueConverter newConverter(Class<? extends ValueConverter> clazz) {
    return clazz.getDeclaredConstructor().newInstance();
  }
}
