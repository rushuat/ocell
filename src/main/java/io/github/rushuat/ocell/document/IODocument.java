package io.github.rushuat.ocell.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class IODocument extends IOStream {

  private final byte[] password;

  protected Workbook workbook;
  protected DocumentStyle style;

  protected IODocument() {
    this(null);
  }

  protected IODocument(String password) {
    IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
    this.workbook = new XSSFWorkbook();
    this.style = new DocumentStyle(workbook);
    this.password =
        Optional.ofNullable(password)
            .filter(Predicate.not(String::isEmpty))
            .map(String::getBytes)
            .orElse(null);
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      if (password == null) {
        workbook = new XSSFWorkbook(stream);
      } else {
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
      }
      style = new DocumentStyle(workbook);
    }
  }

  @Override
  public void toStream(OutputStream stream) throws IOException {
    try (stream) {
      if (password == null) {
        workbook.write(stream);
      } else {
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
      }
    }
  }

  @Override
  public void close() throws IOException {
    workbook.close();
  }
}
