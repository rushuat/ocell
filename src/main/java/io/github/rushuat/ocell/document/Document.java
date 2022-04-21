package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.reflection.DocumentClass;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.poi.ss.usermodel.Sheet;

public class Document extends IODocument {

  public Document() {
    super();
  }

  public Document(String password) {
    super(password);
  }


  public <T> void addSheet(T[] items) {
    addSheet(null, items);
  }

  public <T> void addSheet(Collection<T> items) {
    addSheet(null, items);
  }

  public <T> void addSheet(String name, T[] items) {
    DocumentClass<T> documentClass =
        Optional.ofNullable(items)
            .map(DocumentClass::new)
            .filter(Predicate.not(clazz -> clazz.getType().equals(Object.class)))
            .orElseGet(() ->
                Optional.ofNullable(items)
                    .filter(array -> array.length > 0)
                    .map(array -> array[0])
                    .map(DocumentClass::new)
                    .filter(Predicate.not(clazz -> clazz.getType().equals(Object.class)))
                    .orElse(null)
            );
    Collection<T> itemCollection =
        Optional.ofNullable(items)
            .map(Arrays::asList)
            .orElse(Collections.emptyList());
    addSheet(name, itemCollection, documentClass);
  }

  public <T> void addSheet(String name, Collection<T> items) {
    DocumentClass<T> documentClass =
        Optional.ofNullable(items)
            .map(Collection::iterator)
            .filter(Iterator::hasNext)
            .map(Iterator::next)
            .map(DocumentClass::new)
            .filter(Predicate.not(clazz -> clazz.getType().equals(Object.class)))
            .orElse(null);
    Collection<T> itemCollection =
        Optional.ofNullable(items)
            .orElse(Collections.emptyList());
    addSheet(name, itemCollection, documentClass);
  }

  private <T> void addSheet(String name, Collection<T> items, DocumentClass<T> clazz) {
    if (clazz != null) {
      String sheetName = Optional.ofNullable(name).orElse(clazz.getName());
      Sheet sheet = workbook.createSheet(sheetName);
      DocumentSheet<T> documentSheet = new DocumentSheet<>(sheet, style, clazz);
      documentSheet.addRows(items);
      documentSheet.autoSize();
    }
  }


  public <T> List<T> getSheet(Class<T> clazz) {
    return getSheet(null, clazz);
  }

  public <T> List<T> getSheet(int index, Class<T> clazz) {
    return
        index > 0 && index < workbook.getNumberOfSheets()
            ? getSheet(workbook.getSheetName(index), clazz)
            : Collections.emptyList();
  }

  public <T> List<T> getSheet(String name, Class<T> clazz) {
    return
        Optional.ofNullable(clazz)
            .map(DocumentClass::new)
            .filter(Predicate.not(documentClass -> documentClass.getType().equals(Object.class)))
            .map(documentClass -> {
              String sheetName = Optional.ofNullable(name).orElse(documentClass.getName());
              Sheet sheet = workbook.getSheet(sheetName);
              return
                  Optional.ofNullable(sheet)
                      .map(value -> new DocumentSheet<>(sheet, style, documentClass))
                      .map(DocumentSheet::getRows)
                      .orElse(Collections.emptyList());
            })
            .orElse(Collections.emptyList());
  }
}
