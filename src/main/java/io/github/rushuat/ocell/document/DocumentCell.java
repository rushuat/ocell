package io.github.rushuat.ocell.document;

import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;

public class DocumentCell {

  private final Cell cell;

  public DocumentCell(Cell cell) {
    this.cell = cell;
  }

  public void setStyle(CellStyle style) {
    cell.setCellStyle(style);
  }

  public <E> void setValue(E obj) {
    if (obj != null) {
      if (obj instanceof String) {
        cell.setCellValue((String) obj);
      } else if (obj instanceof Boolean) {
        cell.setCellValue((Boolean) obj);
      } else if (obj instanceof Number) {
        cell.setCellValue(((Number) obj).doubleValue());
      } else if (obj instanceof Date) {
        cell.setCellValue((Date) obj);
      }
    }
  }

  public <V> V getValue() {
    Object value = null;
    if (cell != null) {
      switch (cell.getCellType()) {
        case STRING:
          value = cell.getStringCellValue();
          break;
        case BOOLEAN:
          value = cell.getBooleanCellValue();
          break;
        case NUMERIC:
          value =
              DateUtil.isCellDateFormatted(cell)
                  ? cell.getDateCellValue()
                  : cell.getNumericCellValue();
          break;
        default:
          value = null;
      }
    }
    return (V) value;
  }
}
