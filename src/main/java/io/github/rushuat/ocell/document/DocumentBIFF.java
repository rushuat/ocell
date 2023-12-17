package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.MappingType;
import io.github.rushuat.ocell.field.ValueConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class DocumentBIFF extends Document {

  public DocumentBIFF(
      String password,
      MappingType mapping,
      Map<Class<?>, ValueConverter> converters) {
    super(new HSSFWorkbook(), password, mapping, converters);
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      try (Workbook book = workbook.getWorkbook()) {
        if (workbook.getPassword() == null) {
          workbook.setWorkbook(new HSSFWorkbook(stream));
        } else {
          Biff8EncryptionKey.setCurrentUserPassword(new String(workbook.getPassword()));
          workbook.setWorkbook(new HSSFWorkbook(stream));
          Biff8EncryptionKey.setCurrentUserPassword(null);
        }
      }
    }
  }

  @Override
  public void toStream(OutputStream stream) throws IOException {
    try (stream) {
      Workbook book = workbook.getWorkbook();
      if (workbook.getPassword() == null) {
        book.write(stream);
      } else {
        Biff8EncryptionKey.setCurrentUserPassword(new String(workbook.getPassword()));
        book.write(stream);
        Biff8EncryptionKey.setCurrentUserPassword(null);
      }
    }
  }
}
