package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.reflection.DocumentField;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DocumentHeader {

  private final Row header;
  private final Map<Integer, String> nameByIndex;
  private final Map<String, Integer> indexByName;

  public DocumentHeader(Row header, DocumentStyle style, List<DocumentField> fields) {
    if (header.getLastCellNum() < 0) {
      IntStream
          .range(0, fields.size())
          .forEach(index -> {
            DocumentField documentField = fields.get(index);
            String name = documentField.getName();
            Alignment alignment = documentField.getHeader();
            Format format = new Format(null, false);

            Cell cell = header.createCell(index);
            cell.setCellStyle(style.getCellStyle(format, alignment));
            cell.setCellValue(name);
          });
    }

    this.header = header;

    this.nameByIndex = getNameByIndexMap();
    this.indexByName = getIndexByNameMap();
  }

  private Map<Integer, String> getNameByIndexMap() {
    Map<Integer, String> map = new LinkedHashMap<>();
    IntStream
        .range(0, header.getLastCellNum())
        .filter(index -> Objects.nonNull(header.getCell(index)))
        .forEach(index -> map.put(index, header.getCell(index).getStringCellValue().trim()));
    return map;
  }

  private Map<String, Integer> getIndexByNameMap() {
    Map<String, Integer> map = new LinkedHashMap<>();
    IntStream
        .range(0, header.getLastCellNum())
        .filter(index -> Objects.nonNull(header.getCell(index)))
        .forEach(index -> map.put(header.getCell(index).getStringCellValue().trim(), index));
    return map;
  }

  public Integer getIndex(String name) {
    return indexByName.get(name);
  }

  public String getName(Integer index) {
    return nameByIndex.get(index);
  }

  public Set<Integer> getIndexes() {
    return nameByIndex.keySet();
  }

  public Set<String> getNames() {
    return indexByName.keySet();
  }
}