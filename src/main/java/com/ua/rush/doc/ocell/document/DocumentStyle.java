package com.ua.rush.doc.ocell.document;

import com.ua.rush.doc.ocell.reflection.DocumentField;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
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
    String format = documentField.getFormat();
    String style =
        documentField.getType().equals(Date.class)
            ? DateFormatConverter.convert(Locale.getDefault(), format)
            : format;
    return getCellStyle(style);
  }

  public CellStyle getCellStyle(String style) {
    return styleCache.computeIfAbsent(style, this::toCellStyle);
  }

  private CellStyle toCellStyle(String style) {
    DataFormat dataFormat = workbook.createDataFormat();
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setDataFormat(dataFormat.getFormat(style));
    return cellStyle;
  }
}
