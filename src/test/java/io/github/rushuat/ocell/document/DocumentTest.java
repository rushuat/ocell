package io.github.rushuat.ocell.document;

import static org.testng.Assert.assertEquals;

import io.github.rushuat.ocell.field.EmptyConverter;
import io.github.rushuat.ocell.model.Jpa;
import io.github.rushuat.ocell.model.Json;
import io.github.rushuat.ocell.model.Pojo;
import io.github.rushuat.ocell.model.Status;
import io.github.rushuat.ocell.model.Xml;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DocumentTest {

  private Object[] models;

  @BeforeTest
  public void before() {
    Pojo pojo =
        Pojo.builder()
            .id((long) 1)
            .name("Updated User")
            .dateOfBirth(new Date(123456789))
            .age(33)
            .percent("30%")
            .rating(0.2525)
            .isNew(false)
            .data(null)
            .updated(new Date(987654321))
            .status(Status.OLD)
            .build();
    pojo.setPassword("******");
    models =
        new Object[]{
            null,
            new Object(),
            pojo,
            new Jpa(),
            new Xml(),
            new Json()
        };
  }

  @DataProvider
  public Object[][] passwords() {
    return
        new Object[][]{
            {Documents.BIFF()
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.BIFF(null)
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.BIFF("")
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.BIFF("******")
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.OOXML()
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.OOXML(null)
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.OOXML("")
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
            {Documents.OOXML("******")
                .format(Short.class, "# ##0")
                .converter(String.class, new EmptyConverter())
                .create()},
        };
  }

  @Test(dataProvider = "passwords")
  public void shouldCreateAndLoadDocument(Document document) throws IOException {
    //WHEN
    byte[] documentData;
    try (document) {
      document.addSheet(new Object[]{models[0]});
      document.addSheet(Collections.singletonList(models[0]));
      document.addSheet(new Object[]{models[1]});
      document.addSheet(List.of(models[1]));
      document.addSheet(new Object[]{models[2], new Pojo(), new Pojo()});
      document.addSheet("Jpa Sheet", new Jpa[]{(Jpa) models[3]});
      document.addSheet("Xml Sheet", List.of(models[4]));
      document.addSheet("Json Sheet", List.of(models[5]));
      documentData = document.toBytes();
    }

    List<?> nullList;
    List<Object> objList;
    List<Jpa> jpaList;
    List<Xml> xmlList;
    List<Json> jsonList;
    List<Pojo> pojoList;
    List<Pojo> badIndexList;
    List<Pojo> badNameList;
    try (document) {
      document.fromBytes(documentData);
      nullList = document.getSheet(null);
      objList = document.getSheet(Object.class);
      pojoList = document.getSheet(0, Pojo.class);
      jpaList = document.getSheet("Jpa Sheet", Jpa.class);
      xmlList = document.getSheet("Xml Sheet", Xml.class);
      jsonList = document.getSheet("Json Sheet", Json.class);
      badIndexList = document.getSheet(100, Pojo.class);
      badNameList = document.getSheet("Pojo Sheet", Pojo.class);
    }

    //THEN
    assertEquals(nullList.size(), 0);
    assertEquals(objList.size(), 0);
    assertEquals(pojoList.size(), 3);
    assertEquals(jpaList.size(), 1);
    assertEquals(xmlList.size(), 1);
    assertEquals(jsonList.size(), 1);
    assertEquals(badIndexList.size(), 0);
    assertEquals(badNameList.size(), 0);

    Pojo pojo = (Pojo) models[2];
    pojo.setGender("MALE");
    pojo.setCurrency('$');
    pojo.setSigned('Y');
    pojo.setCar("Jeep");
    pojo.setCitizen("USA");
    pojo.setFormula("CONCATENATE(2+5,\"!\")");
    pojo.setEmpty(null);
    pojo.setCreated("20140220");
    pojo.setAmount((short) 1000);
    assertEquals(pojoList.get(0), pojo);

    Jpa jpa =
        Jpa.builder()
            .id((long) 0)
            .name("New User")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .age(18)
            .percent("50%")
            .rating(0.1234)
            .isNew(true)
            .data(null)
            .updated(new GregorianCalendar(2020, Calendar.JANUARY, 1, 11, 12, 13).getTime())
            .status(Status.NEW)
            .build();
    jpa.setGender("MALE");
    jpa.setCurrency('$');
    jpa.setSigned('Y');
    jpa.setCar("Jeep");
    jpa.setCitizen("USA");
    jpa.setFormula("CONCATENATE(2+5,\"!\")");
    jpa.setEmpty(null);
    jpa.setCreated("20140220");
    jpa.setAmount((short) 1000);
    assertEquals(jpaList.get(0), jpa);

    Xml xml =
        Xml.builder()
            .id((long) 0)
            .name("New User")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .age(18)
            .percent("50%")
            .rating(0.1234)
            .isNew(true)
            .data(null)
            .updated(new GregorianCalendar(2020, Calendar.JANUARY, 1, 11, 12, 13).getTime())
            .status(Status.NEW)
            .build();
    xml.setGender("MALE");
    xml.setCurrency('$');
    xml.setSigned('Y');
    xml.setCar("Jeep");
    xml.setCitizen("USA");
    xml.setFormula("CONCATENATE(2+5,\"!\")");
    xml.setEmpty(null);
    xml.setCreated("20140220");
    xml.setAmount((short) 1000);
    assertEquals(xmlList.get(0), xml);

    Json json =
        Json.builder()
            .id((long) 0)
            .name("New User")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .age(18)
            .percent("50%")
            .rating(0.1234)
            .isNew(true)
            .data(null)
            .updated(new GregorianCalendar(2020, Calendar.JANUARY, 1, 11, 12, 13).getTime())
            .status(Status.NEW)
            .build();
    json.setGender("MALE");
    json.setCurrency('$');
    json.setSigned('Y');
    json.setCar("Jeep");
    json.setCitizen("USA");
    json.setFormula("CONCATENATE(2+5,\"!\")");
    json.setEmpty(null);
    json.setCreated("20140220");
    json.setAmount((short) 1000);
    assertEquals(jsonList.get(0), json);
  }
}