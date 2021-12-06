package io.github.rushuat.ocell.reflection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import io.github.rushuat.ocell.field.AgeConverter;
import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.PercentConverter;
import io.github.rushuat.ocell.model.Jpa;
import io.github.rushuat.ocell.model.Json;
import io.github.rushuat.ocell.model.Pojo;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DocumentFieldTest {

  private Object[] testCase(Object model) {
    return
        new Object[]{
            model,
            Arrays
                .stream(model.getClass().getDeclaredFields())
                .map(field -> new DocumentField(field, new HashMap<>()))
                .collect(Collectors.toList())
        };
  }

  @DataProvider
  public Object[][] models() {
    return
        new Object[][]{
            testCase(new Jpa()),
            testCase(new Json()),
            testCase(new Pojo())
        };
  }

  @Test(dataProvider = "models")
  public void shouldReturnType(Object model, List<DocumentField> documentFields) {
    //GIVEN
    Field field = model.getClass().getDeclaredFields()[0];
    DocumentField documentField = new DocumentField(field, new HashMap<>());
    //WHEN
    Class<?> clazz = documentField.getType();
    //THEN
    assertNotNull(clazz);
    assertEquals(clazz, field.getType());
  }

  @Test(dataProvider = "models")
  public void shouldReturnOrder(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    int order0 = documentField0.getOrder();
    int order1 = documentField1.getOrder();
    int order2 = documentField2.getOrder();
    int order3 = documentField3.getOrder();
    int order4 = documentField4.getOrder();
    int order5 = documentField5.getOrder();
    int order6 = documentField6.getOrder();
    int order7 = documentField7.getOrder();
    //THEN
    assertEquals(order0, 0);
    assertEquals(order1, 1);
    assertEquals(order2, 2);
    assertEquals(order3, 3);
    assertEquals(order4, 4);
    assertEquals(order5, Integer.MAX_VALUE);
    assertEquals(order6, Integer.MAX_VALUE);
    assertEquals(order7, Integer.MAX_VALUE);
  }

  @Test(dataProvider = "models")
  public void shouldReturnExclude(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    boolean excluded0 = documentField0.isExcluded();
    boolean excluded1 = documentField1.isExcluded();
    boolean excluded2 = documentField2.isExcluded();
    boolean excluded3 = documentField3.isExcluded();
    boolean excluded4 = documentField4.isExcluded();
    boolean excluded5 = documentField5.isExcluded();
    boolean excluded6 = documentField6.isExcluded();
    boolean excluded7 = documentField7.isExcluded();
    //THEN
    assertFalse(excluded0);
    assertFalse(excluded1);
    assertFalse(excluded2);
    assertFalse(excluded3);
    assertFalse(excluded4);
    assertFalse(excluded5);
    assertFalse(excluded6);
    assertTrue(excluded7);
  }

  @Test(dataProvider = "models")
  public void shouldReturnFormat(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    String format0 = documentField0.getFormat();
    String format1 = documentField1.getFormat();
    String format2 = documentField2.getFormat();
    String format3 = documentField3.getFormat();
    String format4 = documentField4.getFormat();
    String format5 = documentField5.getFormat();
    String format6 = documentField6.getFormat();
    String format7 = documentField7.getFormat();
    //THEN
    assertNull(format0);
    assertNull(format1);
    assertEquals(format2, "yyyy-MM-dd'T'HH:mm:ss");
    assertNull(format3);
    assertNull(format4);
    assertEquals(format5, "#0.00");
    assertNull(format6);
    assertNull(format7);
  }

  @Test(dataProvider = "models")
  public void shouldReturnAlignment(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);

    //WHEN
    Alignment alignment0 = documentField0.getAlignment();
    Alignment alignment1 = documentField1.getAlignment();
    Alignment alignment2 = documentField2.getAlignment();
    Alignment alignment3 = documentField3.getAlignment();
    Alignment alignment4 = documentField4.getAlignment();
    Alignment alignment5 = documentField5.getAlignment();
    Alignment alignment6 = documentField6.getAlignment();
    Alignment alignment7 = documentField7.getAlignment();

    //THEN
    assertNotNull(alignment0);
    assertNull(alignment0.getHorizontal());
    assertNull(alignment0.getVertical());

    assertNotNull(alignment1);
    assertNull(alignment1.getHorizontal());
    assertNull(alignment1.getVertical());

    assertNotNull(alignment2);
    assertEquals(alignment2.getHorizontal(), "LEFT");
    assertEquals(alignment2.getVertical(), "TOP");

    assertNotNull(alignment3);
    assertNull(alignment3.getHorizontal());
    assertNull(alignment3.getVertical());

    assertNotNull(alignment4);
    assertNull(alignment4.getHorizontal());
    assertNull(alignment4.getVertical());

    assertNotNull(alignment5);
    assertNull(alignment5.getHorizontal());
    assertNull(alignment5.getVertical());

    assertNotNull(alignment6);
    assertEquals(alignment6.getHorizontal(), "CENTER");
    assertNull(alignment6.getVertical());

    assertNotNull(alignment7);
    assertNull(alignment7.getHorizontal());
    assertNull(alignment7.getVertical());
  }

  @Test(dataProvider = "models")
  public void shouldReturnDefault(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    Object default0 = documentField0.getDefault();
    Object default1 = documentField1.getDefault();
    Object default2 = documentField2.getDefault();
    Object default3 = documentField3.getDefault();
    Object default4 = documentField4.getDefault();
    Object default5 = documentField5.getDefault();
    Object default6 = documentField6.getDefault();
    Object default7 = documentField7.getDefault();
    //THEN
    assertEquals(default0, 0L);
    assertEquals(default1, "New User");
    assertEquals(default2, new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime());
    assertEquals(default3, 18);
    assertEquals(default4, "50%");
    assertEquals(default5, 0.1234);
    assertEquals(default6, true);
    assertNull(default7);
  }

  @Test(dataProvider = "models")
  public void shouldReturnConverter(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    Object converter0 = documentField0.getConverter();
    Object converter1 = documentField1.getConverter();
    Object converter2 = documentField2.getConverter();
    Object converter3 = documentField3.getConverter();
    Object converter4 = documentField4.getConverter();
    Object converter5 = documentField5.getConverter();
    Object converter6 = documentField6.getConverter();
    Object converter7 = documentField7.getConverter();
    //THEN
    assertNull(converter0);
    assertNull(converter1);
    assertNull(converter2);
    assertEquals(converter3.getClass(), AgeConverter.class);
    assertEquals(converter4.getClass(), PercentConverter.class);
    assertNull(converter5);
    assertNull(converter6);
    assertNull(converter7);
  }

  @Test(dataProvider = "models")
  public void shouldReturnName(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    String name0 = documentField0.getName();
    String name1 = documentField1.getName();
    String name2 = documentField2.getName();
    String name3 = documentField3.getName();
    String name4 = documentField4.getName();
    String name5 = documentField5.getName();
    String name6 = documentField6.getName();
    String name7 = documentField7.getName();
    //THEN
    assertEquals(name0, "Id");
    assertEquals(name1, "User Name");
    assertEquals(name2, "Date Of Birth");
    assertEquals(name3, "Age");
    assertEquals(name4, "%");
    assertEquals(name5, "Rating");
    assertEquals(name6, "isNew");
    assertEquals(name7, "data");
  }

  @Test(dataProvider = "models")
  public void shouldReturnNumber(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    Object value0 = documentField0.getNumber((float) 0.0);
    Object value1 = documentField1.getNumber("New User");
    Object value2 = documentField2.getNumber(documentField2.getValue(model));
    Object value3 = documentField3.getNumber((int) 18.0);
    Object value4 = documentField4.getNumber("50%");
    Object value5 = documentField5.getNumber((double) 1234);
    Object value6 = documentField6.getNumber(true);
    Object value7 = documentField7.getNumber(null);
    //THEN
    assertEquals(value0, 0L);
    assertEquals(value1, "New User");
    assertEquals(value2, new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime());
    assertEquals(value3, 18);
    assertEquals(value4, "50%");
    assertEquals(value5, 1234.0);
    assertEquals(value6, true);
    assertNull(value7);
  }

  @Test(dataProvider = "models")
  public void shouldReturnValue(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    Object value0 = documentField0.getValue(model);
    Object value1 = documentField1.getValue(model);
    Object value2 = documentField2.getValue(model);
    Object value3 = documentField3.getValue(model);
    Object value4 = documentField4.getValue(model);
    Object value5 = documentField5.getValue(model);
    Object value6 = documentField6.getValue(model);
    Object value7 = documentField7.getValue(model);
    //THEN
    assertEquals(value0, 0L);
    assertEquals(value1, "New User");
    assertEquals(value2, new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime());
    assertEquals(value3, "18 year(s) old");
    assertEquals(value4, 50);
    assertEquals(value5, 0.1234);
    assertEquals(value6, true);
    assertNull(value7);
  }

  @Test(dataProvider = "models")
  public void shouldUpdateValue(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    //WHEN
    documentField0.setValue(model, null);
    documentField1.setValue(model, "Updated User");
    documentField2.setValue(model, new Date(900090009));
    documentField3.setValue(model, "20 year(s) old");
    documentField4.setValue(model, 70);
    documentField5.setValue(model, 0.5678);
    documentField6.setValue(model, false);
    documentField7.setValue(model, null);

    Object value0 = documentField0.getValue(model);
    Object value1 = documentField1.getValue(model);
    Object value2 = documentField2.getValue(model);
    Object value3 = documentField3.getValue(model);
    Object value4 = documentField4.getValue(model);
    Object value5 = documentField5.getValue(model);
    Object value6 = documentField6.getValue(model);
    Object value7 = documentField7.getValue(model);
    //THEN
    assertEquals(value0, 0L);
    assertEquals(value1, "Updated User");
    assertEquals(value2, new Date(900090009));
    assertEquals(value3, "20 year(s) old");
    assertEquals(value4, 70);
    assertEquals(value5, 0.5678);
    assertEquals(value6, false);
    assertNull(value7);
  }
}