package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DocumentHeader {

  private final Row header;
  private final BidiMap<Integer, String> indexNameMap;

  public DocumentHeader(DocumentWorkbook workbook, Row header, List<DocumentField> fields) {
    this.header = header;
    if (header.getLastCellNum() < 0) {
      IntStream
          .range(0, fields.size())
          .forEach(index -> {
            DocumentField documentField = fields.get(index);
            String name = documentField.getName();
            Alignment alignment = documentField.getHeader();
            Format format = new Format(null, false);

            Cell cell = header.createCell(index);
            cell.setCellStyle(workbook.getCellStyle(format, alignment));
            cell.setCellValue(name);
          });
    }

    this.indexNameMap = new DualLinkedHashBidiMap<>();
    IntStream
        .range(0, header.getLastCellNum())
        .filter(index -> Objects.nonNull(header.getCell(index)))
        .forEach(index ->
            indexNameMap.put(index, header.getCell(index).getStringCellValue().trim())
        );
  }

  public Integer getIndex(String name) {
    return indexNameMap.inverseBidiMap().get(name);
  }

  public String getName(Integer index) {
    return indexNameMap.get(index);
  }

  public Set<Integer> getIndexes() {
    return indexNameMap.keySet();
  }

  public Set<String> getNames() {
    return indexNameMap.inverseBidiMap().keySet();
  }
}