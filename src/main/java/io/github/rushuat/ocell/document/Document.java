package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.model.DocumentSheet;
import io.github.rushuat.ocell.model.DocumentWorkbook;
import io.github.rushuat.ocell.reflection.DocumentClass;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public abstract class Document extends DocumentIO {

  protected final byte[] password;
  protected final DocumentWorkbook workbook;

  protected Document(Workbook workbook, String password) {
    IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
    this.workbook = new DocumentWorkbook(workbook);
    this.password =
        Optional.ofNullable(password)
            .filter(Predicate.not(String::isEmpty))
            .map(String::getBytes)
            .orElse(null);
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
      Workbook book = workbook.getWorkbook();
      String sheetName = Optional.ofNullable(name).orElse(clazz.getName());
      Sheet sheet = book.createSheet(sheetName);
      DocumentSheet<T> documentSheet = new DocumentSheet<>(workbook, sheet, clazz);
      documentSheet.addRows(items);
      documentSheet.autoSize();
    }
  }

  public <T> List<T> getSheet(Class<T> clazz) {
    return getSheet(null, clazz);
  }

  public <T> List<T> getSheet(int index, Class<T> clazz) {
    if (index > 0) {
      Workbook book = workbook.getWorkbook();
      if (index < book.getNumberOfSheets()) {
        return getSheet(book.getSheetName(index), clazz);
      }
    }
    return Collections.emptyList();
  }

  public <T> List<T> getSheet(String name, Class<T> clazz) {
    return
        Optional.ofNullable(clazz)
            .map(DocumentClass::new)
            .filter(Predicate.not(documentClass -> documentClass.getType().equals(Object.class)))
            .map(documentClass -> {
              Workbook book = workbook.getWorkbook();
              String sheetName = Optional.ofNullable(name).orElse(documentClass.getName());
              Sheet sheet = book.getSheet(sheetName);
              return
                  Optional.ofNullable(sheet)
                      .map(value -> new DocumentSheet<>(workbook, sheet, documentClass))
                      .map(DocumentSheet::getRows)
                      .orElse(Collections.emptyList());
            })
            .orElse(Collections.emptyList());
  }

  @Override
  public void close() throws IOException {
    workbook.close();
  }
}
