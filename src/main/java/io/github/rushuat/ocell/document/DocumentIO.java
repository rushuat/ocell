package io.github.rushuat.ocell.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public abstract class DocumentIO implements Closeable {

  public abstract void fromStream(InputStream stream) throws IOException;

  public void fromBytes(byte[] bytes) throws IOException {
    fromStream(new ByteArrayInputStream(bytes));
  }

  public void fromFile(File file) throws IOException {
    fromStream(Files.newInputStream(file.toPath()));
  }

  public void fromFile(String path) throws IOException {
    fromFile(new File(path));
  }

  public abstract void toStream(OutputStream stream) throws IOException;

  public byte[] toBytes() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    toStream(outputStream);
    return outputStream.toByteArray();
  }

  public void toFile(File file) throws IOException {
    Files.write(file.toPath(), toBytes());
  }

  public void toFile(String path) throws IOException {
    toFile(new File(path));
  }
}
