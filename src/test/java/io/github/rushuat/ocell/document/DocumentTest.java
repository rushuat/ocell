package io.github.rushuat.ocell.document;

import static org.testng.Assert.assertEquals;

import io.github.rushuat.ocell.model.Jpa;
import io.github.rushuat.ocell.model.Json;
import io.github.rushuat.ocell.model.Pojo;
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
    models =
        new Object[]{
            null,
            new Object(),
            new Jpa(),
            new Json(),
            Pojo.builder()
                .id((long) 1)
                .name("Updated User")
                .dateOfBirth(new Date(123456789))
                .age(33)
                .rating(0.2525)
                .isNew(false)
                .data(null)
                .build()
        };
  }

  @DataProvider
  public Object[][] passwords() {
    return
        new Object[][]{
            {null},
            {""},
            {"******"}
        };
  }

  @Test(dataProvider = "passwords")
  public void shouldCreateAndLoadDocument(String password) throws IOException {
    //WHEN
    byte[] documentData;
    try (Document document = new Document(password)) {
      document.addSheet(new Object[]{models[0]});
      document.addSheet(Collections.singletonList(models[0]));
      document.addSheet(new Object[]{models[1]});
      document.addSheet(Collections.singletonList(models[1]));
      document.addSheet("Jpa Sheet", new Jpa[]{(Jpa) models[2]});
      document.addSheet("Json Sheet", Collections.singletonList(models[3]));
      document.addSheet(new Object[]{models[4], new Pojo(), new Pojo()});
      documentData = document.toBytes();
    }

    List<?> nullList;
    List<Object> objList;
    List<Jpa> jpaList;
    List<Json> jsonList;
    List<Pojo> pojoList;
    List<Pojo> badIndexList;
    List<Pojo> badNameList;
    try (Document document = new Document(password)) {
      document.fromBytes(documentData);
      nullList = document.getSheet(null);
      objList = document.getSheet(Object.class);
      jpaList = document.getSheet("Jpa Sheet", Jpa.class);
      jsonList = document.getSheet("Json Sheet", Json.class);
      pojoList = document.getSheet(2, Pojo.class);
      badIndexList = document.getSheet(100, Pojo.class);
      badNameList = document.getSheet("Pojo Sheet", Pojo.class);
    }

    //THEN
    assertEquals(nullList.size(), 0);
    assertEquals(objList.size(), 0);
    assertEquals(jpaList.size(), 1);
    assertEquals(jsonList.size(), 1);
    assertEquals(pojoList.size(), 3);
    assertEquals(badIndexList.size(), 0);
    assertEquals(badNameList.size(), 0);

    assertEquals(
        jpaList.get(0),
        Jpa.builder()
            .id((long) 0)
            .name("New User")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .age(18)
            .rating(0.1234)
            .isNew(true)
            .data(null)
            .build()
    );
    assertEquals(jsonList.get(0),
        Json.builder()
            .id((long) 0)
            .name("New User")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .age(18)
            .rating(0.1234)
            .isNew(true)
            .data(null)
            .build()
    );
    assertEquals(pojoList.get(0), models[4]);
  }
}