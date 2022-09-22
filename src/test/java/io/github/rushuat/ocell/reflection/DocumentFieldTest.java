package io.github.rushuat.ocell.reflection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import io.github.rushuat.ocell.field.AgeConverter;
import io.github.rushuat.ocell.field.Alignment;
import io.github.rushuat.ocell.field.Format;
import io.github.rushuat.ocell.field.PercentConverter;
import io.github.rushuat.ocell.model.Jpa;
import io.github.rushuat.ocell.model.Json;
import io.github.rushuat.ocell.model.Pojo;
import io.github.rushuat.ocell.model.Status;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DocumentFieldTest {

  private Object[] testCase(Object model) {
    List<Field> list = new ArrayList<>();
    Collections.addAll(list, model.getClass().getDeclaredFields());
    Collections.addAll(list, model.getClass().getSuperclass().getDeclaredFields());
    List<DocumentField> fields = new ArrayList<>();
    list.forEach(field -> fields.add(new DocumentField(field, new HashMap<>())));
    return new Object[]{model, fields};
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
  public void shouldReturnSubtype(Object model, List<DocumentField> documentFields) {
    //GIVEN
    Field field = model.getClass().getDeclaredFields()[3];
    DocumentField documentField = new DocumentField(field, new HashMap<>());
    //WHEN
    Class<?> clazz = documentField.getSubtype();
    //THEN
    assertNotNull(clazz);
    assertEquals(
        clazz,
        ((ParameterizedType)
            documentField
                .getConverter()
                .getClass()
                .getGenericInterfaces()[0]
        ).getActualTypeArguments()[1]
    );
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    int order0 = documentField0.getOrder();
    int order1 = documentField1.getOrder();
    int order2 = documentField2.getOrder();
    int order3 = documentField3.getOrder();
    int order4 = documentField4.getOrder();
    int order5 = documentField5.getOrder();
    int order6 = documentField6.getOrder();
    int order7 = documentField7.getOrder();
    int order8 = documentField8.getOrder();
    int order9 = documentField9.getOrder();
    int order10 = documentField10.getOrder();
    int order11 = documentField11.getOrder();
    int order12 = documentField12.getOrder();
    int order13 = documentField13.getOrder();
    int order14 = documentField14.getOrder();
    int order15 = documentField15.getOrder();
    int order16 = documentField16.getOrder();
    int order17 = documentField17.getOrder();
    int order18 = documentField18.getOrder();
    //THEN
    assertEquals(order0, 0);
    assertEquals(order1, 1);
    assertEquals(order2, 2);
    assertEquals(order3, 3);
    assertEquals(order4, 4);
    assertEquals(order5, Integer.MAX_VALUE);
    assertEquals(order6, Integer.MAX_VALUE);
    assertEquals(order7, Integer.MAX_VALUE);
    assertEquals(order8, Integer.MAX_VALUE);
    assertEquals(order9, 5);
    assertEquals(order10, Integer.MAX_VALUE);
    assertEquals(order11, Integer.MAX_VALUE);
    assertEquals(order12, Integer.MAX_VALUE);
    assertEquals(order13, Integer.MAX_VALUE);
    assertEquals(order14, Integer.MAX_VALUE);
    assertEquals(order15, Integer.MAX_VALUE);
    assertEquals(order16, Integer.MAX_VALUE);
    assertEquals(order17, 6);
    assertEquals(order18, 7);
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    boolean excluded0 = documentField0.isExcluded();
    boolean excluded1 = documentField1.isExcluded();
    boolean excluded2 = documentField2.isExcluded();
    boolean excluded3 = documentField3.isExcluded();
    boolean excluded4 = documentField4.isExcluded();
    boolean excluded5 = documentField5.isExcluded();
    boolean excluded6 = documentField6.isExcluded();
    boolean excluded7 = documentField7.isExcluded();
    boolean excluded8 = documentField8.isExcluded();
    boolean excluded9 = documentField9.isExcluded();
    boolean excluded10 = documentField10.isExcluded();
    boolean excluded11 = documentField11.isExcluded();
    boolean excluded12 = documentField12.isExcluded();
    boolean excluded13 = documentField13.isExcluded();
    boolean excluded14 = documentField14.isExcluded();
    boolean excluded15 = documentField15.isExcluded();
    boolean excluded16 = documentField16.isExcluded();
    boolean excluded17 = documentField17.isExcluded();
    boolean excluded18 = documentField18.isExcluded();
    //THEN
    assertFalse(excluded0);
    assertFalse(excluded1);
    assertFalse(excluded2);
    assertFalse(excluded3);
    assertFalse(excluded4);
    assertFalse(excluded5);
    assertFalse(excluded6);
    assertTrue(excluded7);
    assertFalse(excluded8);
    assertFalse(excluded9);
    assertTrue(excluded10);
    assertTrue(excluded11);
    assertFalse(excluded12);
    assertFalse(excluded13);
    assertFalse(excluded14);
    assertFalse(excluded15);
    assertFalse(excluded16);
    assertFalse(excluded17);
    assertFalse(excluded18);
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);

    //WHEN
    Format format0 = documentField0.getFormat();
    Format format1 = documentField1.getFormat();
    Format format2 = documentField2.getFormat();
    Format format3 = documentField3.getFormat();
    Format format4 = documentField4.getFormat();
    Format format5 = documentField5.getFormat();
    Format format6 = documentField6.getFormat();
    Format format7 = documentField7.getFormat();
    Format format8 = documentField8.getFormat();
    Format format9 = documentField9.getFormat();
    Format format10 = documentField10.getFormat();
    Format format11 = documentField11.getFormat();
    Format format12 = documentField12.getFormat();
    Format format13 = documentField13.getFormat();
    Format format14 = documentField14.getFormat();
    Format format15 = documentField15.getFormat();
    Format format16 = documentField16.getFormat();
    Format format17 = documentField17.getFormat();
    Format format18 = documentField18.getFormat();

    //THEN
    assertNotNull(format0);
    assertNull(format0.getPattern());
    assertFalse(format0.isDate());

    assertNotNull(format1);
    assertNull(format1.getPattern());
    assertFalse(format1.isDate());

    assertNotNull(format2);
    assertEquals(format2.getPattern(), "yyyy-MM-dd'T'HH:mm:ss");
    assertTrue(format2.isDate());

    assertNotNull(format3);
    assertNull(format3.getPattern());
    assertFalse(format3.isDate());

    assertNotNull(format4);
    assertNull(format4.getPattern());
    assertFalse(format4.isDate());

    assertNotNull(format5);
    assertEquals(format5.getPattern(), "#0.00");
    assertFalse(format5.isDate());

    assertNotNull(format6);
    assertNull(format6.getPattern());
    assertFalse(format6.isDate());

    assertNotNull(format7);
    assertNull(format7.getPattern());
    assertFalse(format7.isDate());

    assertNotNull(format8);
    assertNull(format8.getPattern());
    assertTrue(format8.isDate());

    assertNotNull(format9);
    assertNull(format9.getPattern());
    assertFalse(format9.isDate());

    assertNotNull(format10);
    assertNull(format10.getPattern());
    assertFalse(format10.isDate());

    assertNotNull(format11);
    assertNull(format11.getPattern());
    assertFalse(format11.isDate());

    assertNotNull(format12);
    assertNull(format12.getPattern());
    assertFalse(format12.isDate());

    assertNotNull(format13);
    assertNull(format13.getPattern());
    assertFalse(format13.isDate());

    assertNotNull(format14);
    assertNull(format14.getPattern());
    assertFalse(format14.isDate());

    assertNotNull(format15);
    assertNull(format15.getPattern());
    assertFalse(format15.isDate());

    assertNotNull(format16);
    assertNull(format16.getPattern());
    assertFalse(format16.isDate());

    assertNotNull(format17);
    assertNull(format17.getPattern());
    assertFalse(format17.isDate());

    assertNotNull(format18);
    assertNull(format18.getPattern());
    assertFalse(format18.isDate());
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);

    //WHEN
    Alignment alignment0 = documentField0.getAlignment();
    Alignment alignment1 = documentField1.getAlignment();
    Alignment alignment2 = documentField2.getAlignment();
    Alignment alignment3 = documentField3.getAlignment();
    Alignment alignment4 = documentField4.getAlignment();
    Alignment alignment5 = documentField5.getAlignment();
    Alignment alignment6 = documentField6.getAlignment();
    Alignment alignment7 = documentField7.getAlignment();
    Alignment alignment8 = documentField8.getAlignment();
    Alignment alignment9 = documentField9.getAlignment();
    Alignment alignment10 = documentField10.getAlignment();
    Alignment alignment11 = documentField11.getAlignment();
    Alignment alignment12 = documentField12.getAlignment();
    Alignment alignment13 = documentField13.getAlignment();
    Alignment alignment14 = documentField14.getAlignment();
    Alignment alignment15 = documentField15.getAlignment();
    Alignment alignment16 = documentField16.getAlignment();
    Alignment alignment17 = documentField17.getAlignment();
    Alignment alignment18 = documentField18.getAlignment();

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

    assertNotNull(alignment8);
    assertNull(alignment8.getHorizontal());
    assertEquals(alignment8.getVertical(), "BOTTOM");

    assertNotNull(alignment9);
    assertNull(alignment9.getHorizontal());
    assertNull(alignment9.getVertical());

    assertNotNull(alignment10);
    assertNull(alignment10.getHorizontal());
    assertNull(alignment10.getVertical());

    assertNotNull(alignment11);
    assertNull(alignment11.getHorizontal());
    assertNull(alignment11.getVertical());

    assertNotNull(alignment12);
    assertNull(alignment12.getHorizontal());
    assertNull(alignment12.getVertical());

    assertNotNull(alignment13);
    assertNull(alignment13.getHorizontal());
    assertNull(alignment13.getVertical());

    assertNotNull(alignment14);
    assertNull(alignment14.getHorizontal());
    assertNull(alignment14.getVertical());

    assertNotNull(alignment15);
    assertNull(alignment15.getHorizontal());
    assertNull(alignment15.getVertical());

    assertNotNull(alignment16);
    assertNull(alignment16.getHorizontal());
    assertNull(alignment16.getVertical());

    assertNotNull(alignment17);
    assertNull(alignment17.getHorizontal());
    assertNull(alignment17.getVertical());

    assertNotNull(alignment18);
    assertNull(alignment18.getHorizontal());
    assertNull(alignment18.getVertical());
  }

  @Test(dataProvider = "models")
  public void shouldReturnHeader(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);

    //WHEN
    Alignment header0 = documentField0.getHeader();
    Alignment header1 = documentField1.getHeader();
    Alignment header2 = documentField2.getHeader();
    Alignment header3 = documentField3.getHeader();
    Alignment header4 = documentField4.getHeader();
    Alignment header5 = documentField5.getHeader();
    Alignment header6 = documentField6.getHeader();
    Alignment header7 = documentField7.getHeader();
    Alignment header8 = documentField8.getHeader();
    Alignment header9 = documentField9.getHeader();
    Alignment header10 = documentField10.getHeader();
    Alignment header11 = documentField11.getHeader();
    Alignment header12 = documentField12.getHeader();
    Alignment header13 = documentField13.getHeader();
    Alignment header14 = documentField14.getHeader();
    Alignment header15 = documentField15.getHeader();
    Alignment header16 = documentField16.getHeader();
    Alignment header17 = documentField17.getHeader();
    Alignment header18 = documentField18.getHeader();

    //THEN
    assertNotNull(header0);
    assertNull(header0.getHorizontal());
    assertNull(header0.getVertical());

    assertNotNull(header1);
    assertNull(header1.getHorizontal());
    assertNull(header1.getVertical());

    assertNotNull(header2);
    assertNull(header2.getHorizontal());
    assertNull(header2.getVertical());

    assertNotNull(header3);
    assertNull(header3.getHorizontal());
    assertNull(header3.getVertical());

    assertNotNull(header4);
    assertNull(header4.getHorizontal());
    assertNull(header4.getVertical());

    assertNotNull(header5);
    assertNull(header5.getHorizontal());
    assertNull(header5.getVertical());

    assertNotNull(header6);
    assertNull(header6.getHorizontal());
    assertNull(header6.getVertical());

    assertNotNull(header7);
    assertNull(header7.getHorizontal());
    assertNull(header7.getVertical());

    assertNotNull(header8);
    assertEquals(header8.getHorizontal(), "RIGHT");
    assertNull(header8.getVertical());

    assertNotNull(header9);
    assertNull(header9.getHorizontal());
    assertNull(header9.getVertical());

    assertNotNull(header10);
    assertNull(header10.getHorizontal());
    assertNull(header10.getVertical());

    assertNotNull(header11);
    assertNull(header11.getHorizontal());
    assertNull(header11.getVertical());

    assertNotNull(header12);
    assertNull(header12.getHorizontal());
    assertNull(header12.getVertical());

    assertNotNull(header13);
    assertNull(header13.getHorizontal());
    assertNull(header13.getVertical());

    assertNotNull(header14);
    assertNull(header14.getHorizontal());
    assertNull(header14.getVertical());

    assertNotNull(header15);
    assertNull(header15.getHorizontal());
    assertNull(header15.getVertical());

    assertNotNull(header16);
    assertNull(header16.getHorizontal());
    assertNull(header16.getVertical());

    assertNotNull(header17);
    assertNull(header17.getHorizontal());
    assertNull(header17.getVertical());

    assertNotNull(header18);
    assertNull(header18.getHorizontal());
    assertNull(header18.getVertical());
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    Object default0 = documentField0.getDefault();
    Object default1 = documentField1.getDefault();
    Object default2 = documentField2.getDefault();
    Object default3 = documentField3.getDefault();
    Object default4 = documentField4.getDefault();
    Object default5 = documentField5.getDefault();
    Object default6 = documentField6.getDefault();
    Object default7 = documentField7.getDefault();
    Object default8 = documentField8.getDefault();
    Object default9 = documentField9.getDefault();
    Object default10 = documentField10.getDefault();
    Object default11 = documentField11.getDefault();
    Object default12 = documentField12.getDefault();
    Object default13 = documentField13.getDefault();
    Object default14 = documentField14.getDefault();
    Object default15 = documentField15.getDefault();
    Object default16 = documentField16.getDefault();
    Object default17 = documentField17.getDefault();
    Object default18 = documentField18.getDefault();
    //THEN
    assertEquals(default0, 0L);
    assertEquals(default1, "New User");
    assertEquals(default2, new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime());
    assertEquals(default3, 18);
    assertEquals(default4, "50%");
    assertEquals(default5, 0.1234);
    assertEquals(default6, true);
    assertNull(default7);
    assertEquals(default8, new GregorianCalendar(2020, Calendar.JANUARY, 1, 11, 12, 13).getTime());
    assertEquals(default9, Status.NEW);
    assertNull(default10);
    assertNull(default11);
    assertEquals(default12, "MALE");
    assertNull(default13);
    assertEquals(default14, (float) 55.55);
    assertEquals(default15, '$');
    assertEquals(default16, 'Y');
    assertEquals(default17, "Jeep");
    assertEquals(default18, "USA");
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    Object converter0 = documentField0.getConverter();
    Object converter1 = documentField1.getConverter();
    Object converter2 = documentField2.getConverter();
    Object converter3 = documentField3.getConverter();
    Object converter4 = documentField4.getConverter();
    Object converter5 = documentField5.getConverter();
    Object converter6 = documentField6.getConverter();
    Object converter7 = documentField7.getConverter();
    Object converter8 = documentField8.getConverter();
    Object converter9 = documentField9.getConverter();
    Object converter10 = documentField10.getConverter();
    Object converter11 = documentField11.getConverter();
    Object converter12 = documentField12.getConverter();
    Object converter13 = documentField13.getConverter();
    Object converter14 = documentField14.getConverter();
    Object converter15 = documentField15.getConverter();
    Object converter16 = documentField16.getConverter();
    Object converter17 = documentField17.getConverter();
    Object converter18 = documentField18.getConverter();
    //THEN
    assertNull(converter0);
    assertNull(converter1);
    assertNull(converter2);
    assertEquals(converter3.getClass(), AgeConverter.class);
    assertEquals(converter4.getClass(), PercentConverter.class);
    assertNull(converter5);
    assertNull(converter6);
    assertNull(converter7);
    assertNull(converter8);
    assertNull(converter9);
    assertNull(converter10);
    assertNull(converter11);
    assertNull(converter12);
    assertNull(converter13);
    assertNull(converter14);
    assertNull(converter15);
    assertNull(converter16);
    assertNull(converter17);
    assertNull(converter18);
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    String name0 = documentField0.getName();
    String name1 = documentField1.getName();
    String name2 = documentField2.getName();
    String name3 = documentField3.getName();
    String name4 = documentField4.getName();
    String name5 = documentField5.getName();
    String name6 = documentField6.getName();
    String name7 = documentField7.getName();
    String name8 = documentField8.getName();
    String name9 = documentField9.getName();
    String name10 = documentField10.getName();
    String name11 = documentField11.getName();
    String name12 = documentField12.getName();
    String name13 = documentField13.getName();
    String name14 = documentField14.getName();
    String name15 = documentField15.getName();
    String name16 = documentField16.getName();
    String name17 = documentField17.getName();
    String name18 = documentField18.getName();
    //THEN
    assertEquals(name0, "Id");
    assertEquals(name1, "User Name");
    assertEquals(name2, "Date Of Birth");
    assertEquals(name3, "Age");
    assertEquals(name4, "%");
    assertEquals(name5, "Rating");
    assertEquals(name6, "isNew");
    assertEquals(name7, "data");
    assertEquals(name8, "updated");
    assertEquals(name9, "status");
    assertEquals(name10, "hash");
    assertEquals(name11, "password");
    assertEquals(name12, "gender");
    assertEquals(name13, "Number of children");
    assertEquals(name14, "threshold");
    assertEquals(name15, "currency");
    assertEquals(name16, "signed");
    assertEquals(name17, "car");
    assertEquals(name18, "citizen");
  }

  @Test(dataProvider = "models")
  public void shouldReturnEnum(Object model, List<DocumentField> documentFields) {
    //GIVEN
    DocumentField documentField0 = documentFields.get(0);
    DocumentField documentField1 = documentFields.get(1);
    DocumentField documentField2 = documentFields.get(2);
    DocumentField documentField3 = documentFields.get(3);
    DocumentField documentField4 = documentFields.get(4);
    DocumentField documentField5 = documentFields.get(5);
    DocumentField documentField6 = documentFields.get(6);
    DocumentField documentField7 = documentFields.get(7);
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    Object value0 = documentField0.getEnum((float) 0.0, documentField0.getType());
    Object value1 = documentField1.getEnum("New User", documentField1.getType());
    Object value2 = documentField2.getEnum(new Date(), documentField2.getType());
    Object value3 = documentField3.getEnum((int) 18.0, documentField3.getType());
    Object value4 = documentField4.getEnum("50%", documentField4.getType());
    Object value5 = documentField5.getEnum((double) 1234, documentField5.getType());
    Object value6 = documentField6.getEnum(true, documentField6.getType());
    Object value7 = documentField7.getEnum(null, documentField7.getType());
    Object value8 = documentField8.getEnum(new Date(), documentField8.getType());
    Object value9 = documentField9.getEnum("NEW", documentField9.getType());
    Object value10 = documentField10.getEnum(null, documentField10.getType());
    Object value11 = documentField11.getEnum("******", documentField11.getType());
    Object value12 = documentField12.getEnum("MALE", documentField12.getType());
    Object value13 = documentField13.getEnum(3, documentField13.getType());
    Object value14 = documentField14.getEnum((float) 77.77, documentField14.getType());
    Object value15 = documentField15.getEnum('?', documentField15.getType());
    Object value16 = documentField16.getEnum('Y', documentField16.getType());
    Object value17 = documentField17.getEnum("Jeep", documentField17.getType());
    Object value18 = documentField18.getEnum("USA", documentField18.getType());
    //THEN
    assertNull(value0);
    assertNull(value1);
    assertNull(value2);
    assertNull(value3);
    assertNull(value4);
    assertNull(value5);
    assertNull(value6);
    assertNull(value7);
    assertNull(value8);
    assertEquals(value9, Status.NEW);
    assertNull(value10);
    assertNull(value11);
    assertNull(value12);
    assertNull(value13);
    assertNull(value14);
    assertNull(value15);
    assertNull(value16);
    assertNull(value17);
    assertNull(value18);
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    Object value0 = documentField0.getNumber((float) 0.0, documentField0.getType());
    Object value1 = documentField1.getNumber("New User", documentField1.getType());
    Object value2 = documentField2.getNumber(new Date(), documentField2.getType());
    Object value3 = documentField3.getNumber((int) 18.0, documentField3.getType());
    Object value4 = documentField4.getNumber("50%", documentField4.getType());
    Object value5 = documentField5.getNumber((double) 1234, documentField5.getType());
    Object value6 = documentField6.getNumber(true, documentField6.getType());
    Object value7 = documentField7.getNumber(null, documentField7.getType());
    Object value8 = documentField8.getNumber(new Date(), documentField8.getType());
    Object value9 = documentField9.getNumber("NEW", documentField9.getType());
    Object value10 = documentField10.getNumber(null, documentField10.getType());
    Object value11 = documentField11.getNumber("******", documentField11.getType());
    Object value12 = documentField12.getNumber("MALE", documentField12.getType());
    Object value13 = documentField13.getNumber(3, documentField13.getType());
    Object value14 = documentField14.getNumber(77.77, documentField14.getType());
    Object value15 = documentField15.getNumber('?', documentField15.getType());
    Object value16 = documentField16.getNumber('Y', documentField16.getType());
    Object value17 = documentField17.getNumber("Jeep", documentField17.getType());
    Object value18 = documentField18.getNumber("USA", documentField18.getType());
    //THEN
    assertEquals(value0, 0L);
    assertNull(value1);
    assertNull(value2);
    assertEquals(value3, 18);
    assertNull(value4);
    assertEquals(value5, 1234.0);
    assertNull(value6);
    assertNull(value7);
    assertNull(value8);
    assertNull(value9);
    assertNull(value10);
    assertNull(value11);
    assertNull(value12);
    assertEquals(value13, (byte) 3);
    assertEquals(value14, (float) 77.77);
    assertNull(value15);
    assertNull(value16);
    assertNull(value17);
    assertNull(value18);
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    Object value0 = documentField0.getValue(model);
    Object value1 = documentField1.getValue(model);
    Object value2 = documentField2.getValue(model);
    Object value3 = documentField3.getValue(model);
    Object value4 = documentField4.getValue(model);
    Object value5 = documentField5.getValue(model);
    Object value6 = documentField6.getValue(model);
    Object value7 = documentField7.getValue(model);
    Object value8 = documentField8.getValue(model);
    Object value9 = documentField9.getValue(model);
    Object value10 = documentField10.getValue(model);
    Object value11 = documentField11.getValue(model);
    Object value12 = documentField12.getValue(model);
    Object value13 = documentField13.getValue(model);
    Object value14 = documentField14.getValue(model);
    Object value15 = documentField15.getValue(model);
    Object value16 = documentField16.getValue(model);
    Object value17 = documentField17.getValue(model);
    Object value18 = documentField18.getValue(model);
    //THEN
    assertEquals(value0, 0L);
    assertEquals(value1, "New User");
    assertEquals(value2, new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime());
    assertEquals(value3, "18 year(s) old");
    assertEquals(value4, 50);
    assertEquals(value5, 0.1234);
    assertEquals(value6, true);
    assertNull(value7);
    assertEquals(value8, new GregorianCalendar(2020, Calendar.JANUARY, 1, 11, 12, 13).getTime());
    assertEquals(value9, Status.NEW.name());
    assertNull(value10);
    assertNull(value11);
    assertEquals(value12, "MALE");
    assertEquals(value13, (byte) 3);
    assertEquals(value14, (float) 77.77);
    assertEquals(value15, '?');
    assertEquals(value16, 'Y');
    assertEquals(value17, "Jeep");
    assertEquals(value18, "USA");
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
    DocumentField documentField8 = documentFields.get(8);
    DocumentField documentField9 = documentFields.get(9);
    DocumentField documentField10 = documentFields.get(10);
    DocumentField documentField11 = documentFields.get(11);
    DocumentField documentField12 = documentFields.get(12);
    DocumentField documentField13 = documentFields.get(13);
    DocumentField documentField14 = documentFields.get(14);
    DocumentField documentField15 = documentFields.get(15);
    DocumentField documentField16 = documentFields.get(16);
    DocumentField documentField17 = documentFields.get(17);
    DocumentField documentField18 = documentFields.get(18);
    //WHEN
    documentField0.setValue(model, null);
    documentField1.setValue(model, "Updated User");
    documentField2.setValue(model, new Date(900090009));
    documentField3.setValue(model, "20 year(s) old");
    documentField4.setValue(model, 70);
    documentField5.setValue(model, 0.5678);
    documentField6.setValue(model, false);
    documentField7.setValue(model, null);
    documentField8.setValue(model, new Date(100010001));
    documentField9.setValue(model, Status.OLD);
    documentField10.setValue(model, null);
    documentField11.setValue(model, null);
    documentField12.setValue(model, "FEMALE");
    documentField13.setValue(model, 1);
    documentField14.setValue(model, 55.55);
    documentField15.setValue(model, '$');
    documentField16.setValue(model, 'N');
    documentField17.setValue(model, "Honda");
    documentField18.setValue(model, "JPN");

    Object value0 = documentField0.getValue(model);
    Object value1 = documentField1.getValue(model);
    Object value2 = documentField2.getValue(model);
    Object value3 = documentField3.getValue(model);
    Object value4 = documentField4.getValue(model);
    Object value5 = documentField5.getValue(model);
    Object value6 = documentField6.getValue(model);
    Object value7 = documentField7.getValue(model);
    Object value8 = documentField8.getValue(model);
    Object value9 = documentField9.getValue(model);
    Object value10 = documentField10.getValue(model);
    Object value11 = documentField11.getValue(model);
    Object value12 = documentField12.getValue(model);
    Object value13 = documentField13.getValue(model);
    Object value14 = documentField14.getValue(model);
    Object value15 = documentField15.getValue(model);
    Object value16 = documentField16.getValue(model);
    Object value17 = documentField17.getValue(model);
    Object value18 = documentField18.getValue(model);
    //THEN
    assertEquals(value0, 0L);
    assertEquals(value1, "Updated User");
    assertEquals(value2, new Date(900090009));
    assertEquals(value3, "20 year(s) old");
    assertEquals(value4, 70);
    assertEquals(value5, 0.5678);
    assertEquals(value6, false);
    assertNull(value7);
    assertEquals(value8, new Date(100010001));
    assertEquals(value9, Status.OLD.name());
    assertNull(value10);
    assertNull(value11);
    assertEquals(value12, "FEMALE");
    assertEquals(value13, (byte) 1);
    assertEquals(value14, (float) 55.55);
    assertEquals(value15, '$');
    assertEquals(value16, 'N');
    assertEquals(value17, "Honda");
    assertEquals(value18, "JPN");
  }
}