package io.github.rushuat.ocell.reflection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.rushuat.ocell.annotation.ClassName;
import io.github.rushuat.ocell.field.ValueConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
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
import lombok.SneakyThrows;

public class DocumentClass<T> {

  private final Class<T> clazz;
  private final Map<Class<?>, ValueConverter> typeConverters;
  private final Map<Class<? extends ValueConverter>, ValueConverter> fieldConverters;

  public DocumentClass(Class<T> clazz, Map<Class<?>, ValueConverter> typeConverters) {
    this.clazz = clazz;
    this.typeConverters = typeConverters;
    this.fieldConverters = new ConcurrentHashMap<>();
  }

  public DocumentClass(T element, Map<Class<?>, ValueConverter> typeConverters) {
    this((Class<T>) element.getClass(), typeConverters);
  }

  public DocumentClass(T[] array, Map<Class<?>, ValueConverter> typeConverters) {
    this((Class<T>) array.getClass().getComponentType(), typeConverters);
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
          .map(field -> new DocumentField(field, typeConverters, fieldConverters))
          .filter(Predicate.not(DocumentField::isExcluded))
          .forEach(fields::add);
      subclass = subclass.getSuperclass();
    }
    fields.sort(Comparator.comparing(DocumentField::getOrder));
    if (clazz.isAnnotationPresent(XmlAccessorOrder.class)) {
      XmlAccessorOrder accessorOrder = clazz.getAnnotation(XmlAccessorOrder.class);
      if (accessorOrder.value() == XmlAccessOrder.ALPHABETICAL) {
        fields.sort(Comparator.comparing(DocumentField::getName, Collator.getInstance()));
      }
    }
    if (clazz.isAnnotationPresent(JsonPropertyOrder.class)) {
      JsonPropertyOrder propertyOrder = clazz.getAnnotation(JsonPropertyOrder.class);
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
    if (clazz.isAnnotationPresent(XmlRootElement.class)) {
      name = clazz.getAnnotation(XmlRootElement.class).name();
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
