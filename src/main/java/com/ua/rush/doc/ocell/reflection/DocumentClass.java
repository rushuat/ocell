package com.ua.rush.doc.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ua.rush.doc.ocell.annotation.ClassName;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.SneakyThrows;

public class DocumentClass<T> {

  private Class<T> clazz;

  public DocumentClass(Class<T> clazz) {
    this.clazz = clazz;
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
    List<DocumentField> fields =
        Arrays
            .stream(clazz.getDeclaredFields())
            .map(DocumentField::new)
            .sorted(Comparator.comparing(DocumentField::getOrder))
            .collect(Collectors.toList());
    if (clazz.isAnnotationPresent(JsonPropertyOrder.class)) {
      JsonPropertyOrder propertyOrder = clazz.getAnnotation(JsonPropertyOrder.class);
      if (propertyOrder.value().length > 0) {
        Map<String, DocumentField> map =
            fields
                .stream()
                .collect(Collectors.toMap(DocumentField::getName, Function.identity()));
        fields =
            Arrays
                .stream(propertyOrder.value())
                .map(map::get)
                .collect(Collectors.toList());
      }
      if (propertyOrder.alphabetic()) {
        fields.sort(Comparator.comparing(DocumentField::getName, Collator.getInstance()));
      }
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
