package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    Alignment alignment = documentField.getAlignment();
    String format = documentField.getFormat();
    if (format != null) {
      if (documentField.getType().equals(Date.class)) {
        format = DateFormatConverter.convert(Locale.getDefault(), format);
      }
    }
    return getCellStyle(format, alignment);
  }

  public CellStyle getCellStyle(String format, Alignment alignment) {
    String styleKey = format + "+" + alignment;
    return styleCache.computeIfAbsent(styleKey, key -> toCellStyle(format, alignment));
  }

  private CellStyle toCellStyle(String format, Alignment alignment) {
    DataFormat dataFormat = workbook.createDataFormat();
    CellStyle cellStyle = workbook.createCellStyle();
    if (format != null) {
      cellStyle.setDataFormat(dataFormat.getFormat(format));
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
