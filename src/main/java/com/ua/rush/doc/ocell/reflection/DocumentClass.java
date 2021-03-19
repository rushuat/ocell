package com.ua.rush.doc.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ua.rush.doc.ocell.annotation.ClassName;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    if (clazz.isAnnotationPresent(JsonPropertyOrder.class)) {
      String[] fields = clazz.getAnnotation(JsonPropertyOrder.class).value();
      IntStream
          .range(0, fields.length)
          .forEach(index -> map.put(index, fields[index]));
    } else {
      List<DocumentField> fields = getFields();
      IntStream
          .range(0, fields.size())
          .forEach(index -> map.put(index, fields.get(index).getName()));
    }
    return map;
  }

  public Map<String, Integer> getIndexByNameMap() {
    Map<String, Integer> map = new LinkedHashMap<>();
    if (clazz.isAnnotationPresent(JsonPropertyOrder.class)) {
      String[] fields = clazz.getAnnotation(JsonPropertyOrder.class).value();
      IntStream
          .range(0, fields.length)
          .forEach(index -> map.put(fields[index], index));
    } else {
      List<DocumentField> fields = getFields();
      IntStream
          .range(0, fields.size())
          .forEach(index -> map.put(fields.get(index).getName(), index));
    }
    return map;
  }

  public List<DocumentField> getFields() {
    return
        Arrays
            .stream(clazz.getDeclaredFields())
            .map(DocumentField::new)
            .sorted(Comparator.comparing(DocumentField::getOrder))
            .collect(Collectors.toList());
  }

  public String getName() {
    String name = null;
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
