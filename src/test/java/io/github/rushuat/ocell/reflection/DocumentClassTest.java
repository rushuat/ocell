package io.github.rushuat.ocell.reflection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import io.github.rushuat.ocell.field.EmptyConverter;
import io.github.rushuat.ocell.model.Jpa;
import io.github.rushuat.ocell.model.Json;
import io.github.rushuat.ocell.model.Pojo;
import io.github.rushuat.ocell.model.Xml;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DocumentClassTest {

  private Object[] testCase(Object model) {
    return
        new Object[]{
            model,
            new DocumentClass<>(model, Map.of(String.class, new EmptyConverter()))
        };
  }

  @DataProvider
  public Object[][] models() {
    return
        new Object[][]{
            testCase(new Jpa()),
            testCase(new Xml()),
            testCase(new Json()),
            testCase(new Pojo())
        };
  }

  @Test(dataProvider = "models")
  public void shouldCreateObject(Object model, DocumentClass<?> documentClass) {
    //WHEN
    Object obj = documentClass.newInstance();
    //THEN
    assertNotNull(obj);
  }

  @Test(dataProvider = "models")
  public void shouldReturnName(Object model, DocumentClass<?> documentClass) {
    //WHEN
    String name = documentClass.getName();
    //THEN
    assertNotNull(name);
    assertEquals(name, "User");
  }

  @Test(dataProvider = "models")
  public void shouldReturnType(Object model, DocumentClass<?> documentClass) {
    //WHEN
    Class<?> clazz = documentClass.getType();
    //THEN
    assertNotNull(clazz);
    assertEquals(clazz, model.getClass());
  }

  @Test(dataProvider = "models")
  public void shouldReturnFields(Object model, DocumentClass<?> documentClass) {
    //WHEN
    List<DocumentField> documentFields = documentClass.getFields();
    //THEN
    assertNotNull(documentFields);
    assertEquals(documentFields.size(), 21);
  }

  @Test(dataProvider = "models")
  public void shouldReturnMaps(Object model, DocumentClass<?> documentClass) {
    //WHEN
    Map<String, Integer> names = documentClass.getIndexByNameMap();
    Map<Integer, String> indexes = documentClass.getNameByIndexMap();
    //THEN
    assertNotNull(names);
    assertNotNull(indexes);
    assertEquals(names.keySet().size(), 21);
    assertEquals(indexes.keySet().size(), 21);
  }

  @Test(dataProvider = "models")
  public void shouldReturnOrdered(Object model, DocumentClass<?> documentClass) {
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