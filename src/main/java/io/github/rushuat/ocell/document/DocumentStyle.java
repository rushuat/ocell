package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;

public class DocumentStyle {

  private Workbook workbook;
  private Map<String, CellStyle> styleCache;

  public DocumentStyle(Workbook workbook) {
    this.workbook = workbook;
    this.styleCache = new ConcurrentHashMap<>();
  }

  public CellStyle getCellStyle(DocumentField documentField) {
    Format format = documentField.getFormat();
    Alignment alignment = documentField.getAlignment();
    return getCellStyle(format, alignment);
  }

  public CellStyle getCellStyle(Format format, Alignment alignment) {
    String styleKey = format + "+" + alignment;
    return styleCache.computeIfAbsent(styleKey, key -> toCellStyle(format, alignment));
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
}
