package io.github.rushuat.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.rushuat.ocell.annotation.ClassName;
import io.github.rushuat.ocell.field.ValueConverter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.SneakyThrows;

public class DocumentClass<T> {

  private final Class<T> clazz;
  private final Map<Class<? extends ValueConverter>, ValueConverter> converterCache;

  public DocumentClass(Class<T> clazz) {
    this.clazz = clazz;
    this.converterCache = new ConcurrentHashMap<>();
  }

  public DocumentClass(T element) {
    this((Class<T>) element.getClass());
  }

  public DocumentClass(T[] array) {
    this((Class<T>) array.getClass().getComponentType());
  }

  public Map<Integer, String> getNameByIndexMap() {
    Map<Integer, String> map = new LinkedHashMap<>();
    List<DocumentField> fields = getFields();
    IntStream
        .range(0, fields.size())
        .forEach(index -> map.put(index, fields.get(index).getName()));
    return map;
  }

  public Map<String, Integer> getIndexByNameMap() {
    Map<String, Integer> map = new LinkedHashMap<>();
    List<DocumentField> fields = getFields();
    IntStream
        .range(0, fields.size())
        .forEach(index -> map.put(fields.get(index).getName(), index));
    return map;
  }

  public List<DocumentField> getFields() {
    List<DocumentField> fields = new ArrayList<>();
    Class<?> subclass = clazz;
    while (subclass != Object.class) {
      Arrays
          .stream(subclass.getDeclaredFields())
          .map(field -> new DocumentField(field, converterCache))
          .filter(Predicate.not(DocumentField::isExcluded))
          .forEach(fields::add);
      subclass = subclass.getSuperclass();
    }
    if (clazz.isAnnotationPresent(JsonPropertyOrder.class)) {
      JsonPropertyOrder propertyOrder = clazz.getAnnotation(JsonPropertyOrder.class);
      if (propertyOrder.alphabetic()) {
        fields.sort(Comparator.comparing(DocumentField::getName, Collator.getInstance()));
      } else {
        fields.sort(Comparator.comparing(DocumentField::getOrder));
      }
    } else {
      fields.sort(Comparator.comparing(DocumentField::getOrder));
    }
    return fields;
  }

  public String getName() {
    String name = null;
    if (clazz.isAnnotationPresent(Entity.class)) {
      name = clazz.getAnnotation(Entity.class).name();
    }
    if (clazz.isAnnotationPresent(Table.class)) {
      name = clazz.getAnnotation(Table.class).name();
    }
    if (clazz.isAnnotationPresent(JsonTypeName.class)) {
      name = clazz.getAnnotation(JsonTypeName.class).value();
    }
    if (clazz.isAnnotationPresent(ClassName.class)) {
      name = clazz.getAnnotation(ClassName.class).value();
    }
    if (name == null || name.isBlank()) {
      name = clazz.getSimpleName();
    }
    return name;
  }

  public Class<T> getType() {
    return clazz;
  }

  @SneakyThrows
  public T newInstance() {
    return clazz.getDeclaredConstructor().newInstance();
  }
}
