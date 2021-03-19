package com.ua.rush.doc.ocell.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.ua.rush.doc.ocell.Report;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DocumentClassTest {

  private DocumentClass<Report> documentClass;

  @BeforeTest
  public void before() {
    Report[] reports = new Report[]{};
    documentClass = new DocumentClass<>(reports);
  }

  @Test
  public void shouldCreateObject() {
    //WHEN
    Report report = documentClass.newInstance();
    //THEN
    assertNotNull(report);
  }

  @Test
  public void shouldReturnName() {
    //WHEN
    String reportName = documentClass.getName();
    //THEN
    assertNotNull(reportName);
    assertEquals(reportName, "OCell");
  }

  @Test
  public void shouldReturnType() {
    //WHEN
    Class<Report> reportClass = documentClass.getType();
    //THEN
    assertNotNull(reportClass);
    assertEquals(reportClass, Report.class);
  }

  @Test
  public void shouldReturnFields() {
    //WHEN
    List<DocumentField> documentFields = documentClass.getFields();
    //THEN
    assertNotNull(documentFields);
    assertEquals(documentFields.size(), Report.class.getDeclaredFields().length);
  }

  @Test
  public void shouldReturnMaps() {
    //WHEN
    Map<String, Integer> names = documentClass.getIndexByNameMap();
    Map<Integer, String> indexes = documentClass.getNameByIndexMap();
    //THEN
    assertNotNull(names);
    assertNotNull(indexes);
    assertEquals(names.keySet().size(), Report.class.getDeclaredFields().length);
    assertEquals(indexes.keySet().size(), Report.class.getDeclaredFields().length);
  }

  @Test
  public void shouldReturnOrdered() {
    //WHEN
    Integer[] orders =
        documentClass.getFields().stream()
            .map(DocumentField::getOrder)
            .toArray(Integer[]::new);

    boolean ordered = true;
    for (int i = 1; i < orders.length; i++) {
      ordered = ordered && orders[i - 1] <= orders[i];
    }
    //THEN
    assertTrue(ordered);
  }
}