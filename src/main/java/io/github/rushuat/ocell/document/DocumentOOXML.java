package io.github.rushuat.ocell.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DocumentOOXML extends Document {

  public DocumentOOXML() {
    this(null);
  }

  public DocumentOOXML(String password) {
    super(new XSSFWorkbook(), password);
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      try (Workbook book = workbook.getWorkbook()) {
        if (password == null) {
          workbook.setWorkbook(new XSSFWorkbook(stream));
        } else {
          try (POIFSFileSystem fileSystem = new POIFSFileSystem(stream)) {
            EncryptionInfo encryptionInfo = new EncryptionInfo(fileSystem);
            Decryptor decryptor = Decryptor.getInstance(encryptionInfo);

            if (decryptor.verifyPassword(new String(password))) {
              try (InputStream inputStream = decryptor.getDataStream(fileSystem)) {
                workbook.setWorkbook(new XSSFWorkbook(inputStream));
              }
            } else {
              throw new IOException("Invalid document password");
            }
          } catch (GeneralSecurityException e) {
            throw new IOException(e);
          }
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
        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.standard);
        Encryptor encryptor = encryptionInfo.getEncryptor();
        encryptor.confirmPassword(new String(password));

        try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
          try (OutputStream fileStream = encryptor.getDataStream(fileSystem)) {
            book.write(fileStream);
          }
          fileSystem.writeFilesystem(stream);
        } catch (GeneralSecurityException e) {
          throw new IOException(e);
        }
      }
    }
  }
}
