package com.ua.rush.doc.ocell.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import com.ua.rush.doc.ocell.Report;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DocumentFieldTest {

  private Report report;
  private List<DocumentField> documentFields;

  @BeforeTest
  public void before() {
    report = new Report(0, null, null, null, null);

    documentFields =
        Arrays.stream(report.getClass().getDeclaredFields())
            .map(DocumentField::new)
            .sorted(Comparator.comparing(DocumentField::getOrder))
            .collect(Collectors.toList());
  }

  @Test
  public void shouldReturnType() {
    //GIVEN
    Field field = report.getClass().getDeclaredFields()[0];
    DocumentField documentField = new DocumentField(field);
    //WHEN
    Class<?> clazz = documentField.getType();
    //THEN
    assertNotNull(clazz);
    assertEquals(clazz, field.getType());
  }

  @Test
  public void shouldReturnOrder() {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    //WHEN
    int order0 = documentField0.getOrder();
    int order1 = documentField1.getOrder();
    int order2 = documentField2.getOrder();
    int order3 = documentField3.getOrder();
    int order4 = documentField4.getOrder();
    //THEN
    assertEquals(order0, 0);
    assertEquals(order1, 1);
    assertEquals(order2, 2);
    assertEquals(order3, Integer.MAX_VALUE);
    assertEquals(order4, Integer.MAX_VALUE);
  }

  @Test
  public void shouldReturnFormat() {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    //WHEN
    String format0 = documentField0.getFormat();
    String format1 = documentField1.getFormat();
    String format2 = documentField2.getFormat();
    String format3 = documentField3.getFormat();
    String format4 = documentField4.getFormat();
    //THEN
    assertEquals(format0, "");
    assertEquals(format1, "");
    assertEquals(format2, "yyyy-MM-dd'T'HH:mm:ss");
    assertEquals(format3, "#.00");
    assertEquals(format4, "");
  }

  @Test
  public void shouldReturnName() {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    //WHEN
    String name0 = documentField0.getName();
    String name1 = documentField1.getName();
    String name2 = documentField2.getName();
    String name3 = documentField3.getName();
    String name4 = documentField4.getName();
    //THEN
    assertEquals(name0, "ID");
    assertEquals(name1, "USER_NAME");
    assertEquals(name2, "dateOfBirth");
    assertEquals(name3, "rate");
    assertEquals(name4, "isNew");
  }

  @Test
  public void shouldReturnValue() {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    //WHEN
    Object value0 = documentField0.getValue(report);
    Object value1 = documentField1.getValue(report);
    Object value2 = documentField2.getValue(report);
    Object value3 = documentField3.getValue(report);
    Object value4 = documentField4.getValue(report);
    //THEN
    assertEquals(value0, 0);
    assertEquals(value1, "User Name");
    assertEquals(value2, new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime());
    assertEquals(value3, 0.1234);
    assertEquals(value4, false);
  }

  @Test
  public void shouldUpdateValue() {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    //WHEN
    documentField0.setValue(report, null);
    documentField1.setValue(report, "User Full Name");
    documentField2.setValue(report, new Date(900090009));
    documentField3.setValue(report, 0.5678);
    documentField4.setValue(report, true);

    Object value0 = documentField0.getValue(report);
    Object value1 = documentField1.getValue(report);
    Object value2 = documentField2.getValue(report);
    Object value3 = documentField3.getValue(report);
    Object value4 = documentField4.getValue(report);
    //THEN
    assertNull(value0);
    assertEquals(value1, "User Full Name");
    assertEquals(value2, new Date(900090009));
    assertEquals(value3, 0.5678);
    assertEquals(value4, true);
  }
}