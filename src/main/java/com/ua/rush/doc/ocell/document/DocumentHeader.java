package com.ua.rush.doc.ocell.document;

import com.ua.rush.doc.ocell.reflection.DocumentClass;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DocumentHeader<T> {

  private Row header;
  private Map<Integer, String> nameByIndex;
  private Map<String, Integer> indexByName;

  public DocumentHeader(Row header, DocumentClass<T> clazz) {
    if (header.getLastCellNum() < 0) {
      Map<Integer, String> headers = clazz.getNameByIndexMap();
      headers.keySet()
          .forEach(index -> {
            Cell cell = header.createCell(index);
            cell.setCellValue(headers.get(index));
          });
    }
    this.header = header;

    nameByIndex = getNameByIndexMap();
    indexByName = getIndexByNameMap();
  }

  private Map<Integer, String> getNameByIndexMap() {
    Map<Integer, String> map = new LinkedHashMap<>();
    IntStream
        .range(0, header.getLastCellNum())
        .filter(index -> Objects.nonNull(header.getCell(index)))
        .forEach(index -> map.put(index, header.getCell(index).getStringCellValue()));
    return map;
  }

  private Map<String, Integer> getIndexByNameMap() {
    Map<String, Integer> map = new LinkedHashMap<>();
    IntStream
        .range(0, header.getLastCellNum())
        .filter(index -> Objects.nonNull(header.getCell(index)))
        .forEach(index -> map.put(header.getCell(index).getStringCellValue(), index));
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