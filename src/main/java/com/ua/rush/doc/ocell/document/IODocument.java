package com.ua.rush.doc.ocell.document;

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

public abstract class IODocument extends IOStream {

  private String password;

  protected Workbook workbook;
  protected DocumentStyle style;

  protected IODocument() {
    workbook = new XSSFWorkbook();
    style = new DocumentStyle(workbook);
  }

  protected IODocument(String password) {
    this();
    this.password = password;
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      InputStream inputStream = stream;
      if (password != null && !password.isEmpty()) {
        POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
        EncryptionInfo encryptionInfo = new EncryptionInfo(fileSystem);
        Decryptor decryptor = Decryptor.getInstance(encryptionInfo);

        if (decryptor.verifyPassword(password)) {
          inputStream = decryptor.getDataStream(fileSystem);
        } else {
          throw new IOException("Invalid document password");
        }
      }
      workbook = new XSSFWorkbook(inputStream);
      style = new DocumentStyle(workbook);
    } catch (GeneralSecurityException e) {
      throw new IOException(e);
    }
  }

  @Override
  public void toStream(OutputStream stream) throws IOException {
    try (stream) {
      if (password != null && !password.isEmpty()) {
        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile);
        Encryptor encryptor = encryptionInfo.getEncryptor();
        encryptor.confirmPassword(password);

        try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
          try (OutputStream fileStream = encryptor.getDataStream(fileSystem)) {
            workbook.write(fileStream);
          } catch (GeneralSecurityException e) {
            throw new IOException(e);
          }
          fileSystem.writeFilesystem(stream);
        }
      } else {
        workbook.write(stream);
      }
    }
  }

  @Override
  public void close() throws IOException {
    workbook.close();
  }
}
