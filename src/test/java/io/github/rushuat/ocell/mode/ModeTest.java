package io.github.rushuat.ocell.mode;

import io.github.rushuat.ocell.document.Document;
import io.github.rushuat.ocell.document.Documents;
import io.github.rushuat.ocell.field.MappingMode;
import java.io.IOException;
import java.util.List;
import org.testng.annotations.Test;

public class ModeTest {

  @Test
  public void shouldProcessDocument() throws IOException {
    //WHEN
    byte[] data;
    try (Document document = Documents.OOXML().mode(MappingMode.FLEXIBLE).create()) {
      document.addSheet(List.of(new Model0("field0", "filed1", "filed2")));
      data = document.toBytes();
    }
    try (Document document = Documents.OOXML().mode(MappingMode.FLEXIBLE).create()) {
      document.fromBytes(data);
      document.getSheet(0, Model1.class);
    }
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowException() throws IOException {
    //WHEN
    byte[] data;
    try (Document document = Documents.OOXML().mode(MappingMode.STRICT).create()) {
      document.addSheet(List.of(new Model0("field0", "filed1", "filed2")));
      data = document.toBytes();
    }
    try (Document document = Documents.OOXML().mode(MappingMode.STRICT).create()) {
      document.fromBytes(data);
      document.getSheet(0, Model1.class);
    }
  }
}
