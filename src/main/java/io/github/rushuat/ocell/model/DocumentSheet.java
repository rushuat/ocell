package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.reflection.DocumentClass;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DocumentSheet<T> {

  private final DocumentWorkbook workbook;

  private final Sheet sheet;
  private int headerOffset;
  private int dataOffset;
  private DocumentHeader header;

  private final DocumentClass<T> clazz;
  private final List<DocumentField> fields;

  public DocumentSheet(
      DocumentWorkbook workbook,
      Sheet sheet,
      DocumentClass<T> clazz) {
    this(workbook, sheet, 0, 1, clazz);
  }

  public DocumentSheet(
      DocumentWorkbook workbook,
      Sheet sheet,
      int headerOffset,
      int dataOffset,
      DocumentClass<T> clazz) {
    this.workbook = workbook;

    this.sheet = sheet;
    this.clazz = clazz;
    this.fields = clazz.getFields();

    initOffset(headerOffset, dataOffset);
    initHeader();
  }

  private void initOffset(int headerOffset, int dataOffset) {
    this.headerOffset = headerOffset;
    this.dataOffset = dataOffset <= headerOffset ? headerOffset + 1 : dataOffset;
  }

  private void initHeader() {
    Row row =
        sheet.getLastRowNum() > 0
            ? sheet.getRow(headerOffset)
            : Optional.ofNullable(sheet.getRow(0)).orElse(sheet.createRow(0));
    this.header = new DocumentHeader(workbook, row, fields);
  }

  public void addRows(Collection<T> items) {
    items.forEach(
        item -> {
          Row row = sheet.createRow(sheet.getLastRowNum() + 1);
          DocumentRow<T> documentRow = new DocumentRow<>(workbook, row, header, fields);
          documentRow.setCells(item);
        });
  }

  public List<T> getRows() {
    return
        IntStream
            .range(dataOffset, sheet.getLastRowNum() + 1)
            .mapToObj(
                index -> {
                  T item = clazz.newInstance();
                  Row row = sheet.getRow(index);
                  DocumentRow<T> documentRow = new DocumentRow<>(workbook, row, header, fields);
                  return documentRow.getCells(item);
                })
            .collect(Collectors.toList());
  }

  public void autoSize() {
    header.getIndexes().forEach(sheet::autoSizeColumn);
  }
}
