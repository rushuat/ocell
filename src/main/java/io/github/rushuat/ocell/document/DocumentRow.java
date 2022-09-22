package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.List;
import java.util.Objects;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DocumentRow<T> {

  private final Row row;
  private final DocumentStyle style;
  private final DocumentHeader header;
  private final List<DocumentField> fields;

  public DocumentRow(
      Row row,
      DocumentStyle style,
      DocumentHeader header,
      List<DocumentField> fields) {
    this.row = row;
    this.style = style;
    this.header = header;
    this.fields = fields;
  }

  public void setCells(T item) {
    fields
        .stream()
        .filter(field -> Objects.nonNull(header.getIndex(field.getName())))
        .forEach(field -> {
          Integer index = header.getIndex(field.getName());
          Cell cell = row.createCell(index);
          DocumentCell documentCell = new DocumentCell(cell);
          documentCell.setStyle(style.getCellStyle(field));
          documentCell.setValue(field.getValue(item));
        });
  }

  public T getCells(T item) {
    fields
        .stream()
        .filter(field -> Objects.nonNull(header.getIndex(field.getName())))
        .forEach(field -> {
          Integer index = header.getIndex(field.getName());
          Cell cell = row.getCell(index);
          DocumentCell documentCell = new DocumentCell(cell);
          field.setValue(item, documentCell.getValue());
        });
    return item;
  }
}
