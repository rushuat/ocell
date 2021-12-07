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

public abstract class IODocument extends IOStream {

  private byte[] password;

  protected Workbook workbook;
  protected DocumentStyle style;

  protected IODocument() {
    this.workbook = new XSSFWorkbook();
    this.style = new DocumentStyle(workbook);
  }

  protected IODocument(String password) {
    this();
    this.password = password == null || password.isEmpty() ? null : password.getBytes();
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      if (password != null && password.length > 0) {
        try (POIFSFileSystem fileSystem = new POIFSFileSystem(stream)) {
          EncryptionInfo encryptionInfo = new EncryptionInfo(fileSystem);
          Decryptor decryptor = Decryptor.getInstance(encryptionInfo);

          if (decryptor.verifyPassword(new String(password))) {
            try (InputStream inputStream = decryptor.getDataStream(fileSystem)) {
              workbook = new XSSFWorkbook(inputStream);
            }
          } else {
            throw new IOException("Invalid document password");
          }
        } catch (GeneralSecurityException e) {
          throw new IOException(e);
        }
      } else {
        workbook = new XSSFWorkbook(stream);
      }
      style = new DocumentStyle(workbook);
    }
  }

  @Override
  public void toStream(OutputStream stream) throws IOException {
    try (stream) {
      if (password != null && password.length > 0) {
        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.standard);
        Encryptor encryptor = encryptionInfo.getEncryptor();
        encryptor.confirmPassword(new String(password));

        try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
          try (OutputStream fileStream = encryptor.getDataStream(fileSystem)) {
            workbook.write(fileStream);
          }
          fileSystem.writeFilesystem(stream);
        } catch (GeneralSecurityException e) {
          throw new IOException(e);
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
