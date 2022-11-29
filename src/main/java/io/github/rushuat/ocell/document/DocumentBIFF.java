package io.github.rushuat.ocell.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class DocumentBIFF extends Document {

  public DocumentBIFF() {
    this(null);
  }

  public DocumentBIFF(String password) {
    super(new HSSFWorkbook(), password);
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      try (Workbook book = workbook.getWorkbook()) {
        if (password == null) {
          workbook.setWorkbook(new HSSFWorkbook(stream));
        } else {
          Biff8EncryptionKey.setCurrentUserPassword(new String(password));
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
      if (password == null) {
        book.write(stream);
      } else {
        Biff8EncryptionKey.setCurrentUserPassword(new String(password));
        book.write(stream);
        Biff8EncryptionKey.setCurrentUserPassword(null);
      }
    }
  }
}
