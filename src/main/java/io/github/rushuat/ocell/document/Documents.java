package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.ValueConverter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Documents {

  private Documents() {
  }

  private enum DocumentType {
    BIFF, OOXML
  }

  public static class DocumentBuilder {

    private final DocumentType type;
    private final String password;
    private final Map<Class<?>, ValueConverter> converters;

    private DocumentBuilder(DocumentType type, String password) {
      this.type = type;
      this.password = password;
      this.converters = new ConcurrentHashMap<>();
    }

    public DocumentBuilder converter(Class<?> clazz, ValueConverter converter) {
      if (clazz != null && converter != null) {
        this.converters.put(clazz, converter);
      }
      return this;
    }

    public DocumentBuilder converters(Map<Class<?>, ValueConverter> converters) {
      if (converters != null) {
        this.converters.putAll(converters);
      }
      return this;
    }

    public Document create() {
      switch (type) {
        case BIFF:
          return new DocumentBIFF(password, converters);
        case OOXML:
          return new DocumentOOXML(password, converters);
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
