package com.ua.rush.doc.ocell.document;

import com.ua.rush.doc.ocell.reflection.DocumentClass;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Sheet;

public class Document extends IODocument {

  public Document() {
    super();
  }

  public Document(String password) {
    super(password);
  }


  public <T> void addSheet(T... items) {
    addSheet(null, items);
  }

  public <T> void addSheet(List<T> items) {
    addSheet(null, items);
  }

  public <T> void addSheet(String name, T... items) {
    DocumentClass<T> documentClass =
        Optional.ofNullable(items)
            .map(DocumentClass::new)
            .filter(clazz -> !clazz.getType().equals(Object.class))
            .orElse(null);
    List<T> itemList =
        Optional.ofNullable(items)
            .map(Arrays::asList)
            .orElse(Collections.emptyList());
    addSheet(name, itemList, documentClass);
  }

  public <T> void addSheet(String name, List<T> items) {
    DocumentClass<T> documentClass =
        Optional.ofNullable(items)
            .filter(list -> !list.isEmpty())
            .map(list -> new DocumentClass<>(list.get(0)))
            .filter(clazz -> !clazz.getType().equals(Object.class))
            .orElse(null);
    List<T> itemList =
        Optional.ofNullable(items)
            .orElse(Collections.emptyList());
    addSheet(name, itemList, documentClass);
  }

  private <T> void addSheet(String name, List<T> items, DocumentClass<T> clazz) {
    if (clazz != null) {
      String sheetName = Optional.ofNullable(name).orElse(clazz.getName());
      Sheet sheet = workbook.createSheet(sheetName);
      DocumentSheet<T> documentSheet = new DocumentSheet<>(sheet, style, clazz);
      documentSheet.addRows(items);
      documentSheet.autoSize();
    }
  }


  public <T> List<T> getSheet(Class<T> clazz) {
    return getSheet(null, 0, 1, clazz);
  }

  public <T> List<T> getSheet(String name, Class<T> clazz) {
    return getSheet(name, 0, 1, clazz);
  }

  private <T> List<T> getSheet(
      String name,
      Integer headerOffset,
      Integer dataOffset,
      Class<T> clazz) {
    return
        Optional.ofNullable(clazz)
            .map(DocumentClass::new)
            .filter(documentClass -> !documentClass.getType().equals(Object.class))
            .map(documentClass -> {
              String sheetName = Optional.ofNullable(name).orElse(documentClass.getName());
              Sheet sheet = workbook.getSheet(sheetName);
              DocumentSheet<T> documentSheet =
                  new DocumentSheet<>(sheet, headerOffset, dataOffset, style, documentClass);
              return documentSheet.getRows();
            })
            .orElse(Collections.emptyList());
  }
}
