package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.field.MappingMode;
import io.github.rushuat.ocell.reflection.DocumentClass;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
    validateHeader();
  }

  private void initOffset(int headerOffset, int dataOffset) {
    this.headerOffset = headerOffset;
    this.dataOffset = dataOffset <= headerOffset ? headerOffset + 1 : dataOffset;
  }

  private void initHeader() {
    Row row =
        sheet.getLastRowNum() > 0
            ? sheet.getRow(headerOffset)
            : Optional.ofNullable(sheet.getRow(0)).orElseGet(() -> sheet.createRow(0));
    this.header = new DocumentHeader(workbook, row, fields);
  }

  private void validateHeader() {
    if (workbook.getMode() == MappingMode.STRICT) {
      Set<String> requiredFields =
          fields
              .stream()
              .map(DocumentField::getName)
              .collect(Collectors.toSet());
      List<String> missingFields =
          header.getNames()
              .stream()
              .filter(name -> !requiredFields.contains(name))
              .collect(Collectors.toList());
      List<String> extraFields =
          requiredFields
              .stream()
              .filter(name -> Objects.isNull(header.getIndex(name)))
              .collect(Collectors.toList());
      if (!missingFields.isEmpty() || !extraFields.isEmpty()) {
        throw
            new IllegalArgumentException(
                clazz.getType().getName() + ": missing " + missingFields + " extra " + extraFields
            );
      }
    }
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
