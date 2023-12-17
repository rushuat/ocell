package io.github.rushuat.ocell.document;

import io.github.rushuat.ocell.field.MappingType;
import io.github.rushuat.ocell.field.ValueConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Map;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DocumentOOXML extends Document {

  public DocumentOOXML(
      String password,
      MappingType mapping,
      Map<Class<?>, ValueConverter> converters) {
    super(new XSSFWorkbook(), password, mapping, converters);
  }

  @Override
  public void fromStream(InputStream stream) throws IOException {
    try (stream) {
      try (Workbook book = workbook.getWorkbook()) {
        if (workbook.getPassword() == null) {
          workbook.setWorkbook(new XSSFWorkbook(stream));
        } else {
          try (POIFSFileSystem fileSystem = new POIFSFileSystem(stream)) {
            EncryptionInfo encryptionInfo = new EncryptionInfo(fileSystem);
            Decryptor decryptor = Decryptor.getInstance(encryptionInfo);

            if (decryptor.verifyPassword(new String(workbook.getPassword()))) {
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
      if (workbook.getPassword() == null) {
        book.write(stream);
      } else {
        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.standard);
        Encryptor encryptor = encryptionInfo.getEncryptor();
        encryptor.confirmPassword(new String(workbook.getPassword()));

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
