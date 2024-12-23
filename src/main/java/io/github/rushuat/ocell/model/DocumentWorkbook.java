package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.field.MappingType;
import io.github.rushuat.ocell.field.ValueConverter;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;

public class DocumentWorkbook implements Closeable {

  private Workbook workbook;
  private Map<String, CellStyle> styles;

  private final byte[] password;
  private final MappingType mapping;
  private final Map<Class<?>, Format> formats;
  private final Map<Class<?>, ValueConverter> converters;

  public DocumentWorkbook(
      Workbook workbook,
      String password,
      MappingType mapping,
      Map<Class<?>, Format> formats,
      Map<Class<?>, ValueConverter> converters) {
    this.workbook = workbook;
    this.styles = new ConcurrentHashMap<>();

    this.password =
        Optional.ofNullable(password)
            .filter(Predicate.not(String::isEmpty))
            .map(String::getBytes)
            .orElse(null);
    this.mapping =
        Optional.ofNullable(mapping)
            .orElse(MappingType.FLEXIBLE);
    this.formats =
        Optional.ofNullable(formats)
            .map(ConcurrentHashMap::new)
            .orElseGet(ConcurrentHashMap::new);
    this.converters =
        Optional.ofNullable(converters)
            .map(ConcurrentHashMap::new)
            .orElseGet(ConcurrentHashMap::new);
  }

  public void setWorkbook(Workbook workbook) {
    this.styles = new ConcurrentHashMap<>();
    this.workbook = workbook;
  }

  public Workbook getWorkbook() {
    return workbook;
  }

  public byte[] getPassword() {
    return password;
  }

  public MappingType getMapping() {
    return mapping;
  }

  public Map<Class<?>, ValueConverter> getConverters() {
    return converters;
  }

  public CellStyle getCellStyle(DocumentField documentField) {
    Format format = documentField.getFormat();
    String pattern = format.getPattern();
    if (pattern == null) {
      Class<?> type = documentField.getExternalType();
      format = formats.getOrDefault(type, format);
    }

    Alignment alignment = documentField.getAlignment();

    return getCellStyle(format, alignment);
  }

  public CellStyle getCellStyle(Format format, Alignment alignment) {
    String styleKey = format + "+" + alignment;
    return styles.computeIfAbsent(styleKey, key -> toCellStyle(format, alignment));
  }

  private CellStyle toCellStyle(Format format, Alignment alignment) {
    DataFormat dataFormat = workbook.createDataFormat();
    CellStyle cellStyle = workbook.createCellStyle();

    String pattern = format.getPattern();
    if (format.isDate()) {
      if (pattern == null) {
        pattern = BuiltinFormats.getBuiltinFormat(14);
      } else {
        if (BuiltinFormats.getBuiltinFormat(pattern) == -1) {
          pattern = DateFormatConverter.convert(Locale.getDefault(), pattern);
        }
      }
    }
    if (pattern != null) {
      cellStyle.setDataFormat(dataFormat.getFormat(pattern));
    }

    if (alignment.getHorizontal() != null) {
      cellStyle.setAlignment(HorizontalAlignment.valueOf(alignment.getHorizontal()));
    }
    if (alignment.getVertical() != null) {
      cellStyle.setVerticalAlignment(VerticalAlignment.valueOf(alignment.getVertical()));
    }

    return cellStyle;
  }

  @Override
  public void close() throws IOException {
    styles = null;
    workbook.close();
  }
}
