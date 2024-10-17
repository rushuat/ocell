package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.field.MappingType;
import io.github.rushuat.ocell.field.ValueConverter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class Documents {

  private Documents() {
  }

  private enum DocumentType {
    BIFF,
    OOXML
  }

  public static class DocumentBuilder {

    private final DocumentType type;
    private final String password;
    private final AtomicReference<MappingType> mapping;
    private final Map<Class<?>, Format> formats;
    private final Map<Class<?>, ValueConverter> converters;

    private DocumentBuilder(DocumentType type, String password) {
      this.type = type;
      this.password = password;
      this.mapping = new AtomicReference<>(MappingType.FLEXIBLE);
      this.formats = new ConcurrentHashMap<>();
      this.converters = new ConcurrentHashMap<>();
    }

    public DocumentBuilder mapping(MappingType mapping) {
      if (mapping != null) {
        this.mapping.set(mapping);
      }
      return this;
    }

    public DocumentBuilder format(Class<?> clazz, String pattern) {
      if (clazz != null && pattern != null && !pattern.isBlank()) {
        boolean isDate = clazz.equals(Date.class);
        Format format = new Format(pattern, isDate);
        this.formats.put(clazz, format);
      }
      return this;
    }

    public DocumentBuilder formats(Map<Class<?>, String> patterns) {
      if (patterns != null) {
        patterns.forEach(this::format);
      }
      return this;
    }

    public DocumentBuilder converter(Class<?> clazz, ValueConverter converter) {
      if (clazz != null && converter != null) {
        this.converters.put(clazz, converter);
      }
      return this;
    }

    public DocumentBuilder converters(Map<Class<?>, ValueConverter> converters) {
      if (converters != null) {
        converters.forEach(this::converter);
      }
      return this;
    }

    public Document create() {
      switch (type) {
        case BIFF:
          return new DocumentBIFF(password, mapping.get(), formats, converters);
        case OOXML:
          return new DocumentOOXML(password, mapping.get(), formats, converters);
        default:
          throw new IllegalArgumentException(type.name());
      }
    }
  }

  public static DocumentBuilder BIFF() {
    return new DocumentBuilder(DocumentType.BIFF, null);
  }

  public static DocumentBuilder BIFF(String password) {
    return new DocumentBuilder(DocumentType.BIFF, password);
  }

  public static DocumentBuilder OOXML() {
    return new DocumentBuilder(DocumentType.OOXML, null);
  }

  public static DocumentBuilder OOXML(String password) {
    return new DocumentBuilder(DocumentType.OOXML, password);
  }
}
