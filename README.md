[![maven-central](https://img.shields.io/maven-central/v/io.github.rushuat/ocell.svg?color=blue)](https://central.sonatype.com/artifact/io.github.rushuat/ocell)
[![javadoc](https://javadoc.io/badge2/io.github.rushuat/ocell/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.rushuat/ocell)
[![license](https://img.shields.io/github/license/rushuat/ocell.svg?color=blue)](https://www.apache.org/licenses/LICENSE-2.0.txt)

# oCell
This is a library for Excel to POJO and POJO to Excel mapping based on [Apache POI](https://poi.apache.org/)

## Usage

### Java
The library is built for **JDK 11+** version usage.

### Dependency
Your project should have a dependency to the library:
```xml
<dependency>
  <groupId>io.github.rushuat</groupId>
  <artifactId>ocell</artifactId>
  <version>0.1.9</version>
</dependency>
```

### Code
The library supports POJOs and few types of annotations to customize them:
1. [oCell](https://github.com/rushuat/ocell/tree/main/src/main/java/io/github/rushuat/ocell/annotation)
2. [Jackson](https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations)
3. [JAXB](https://javadoc.io/doc/javax.xml.bind/jaxb-api/2.3.1/javax/xml/bind/annotation/package-summary.html)
4. [JPA](https://javadoc.io/static/javax.persistence/javax.persistence-api/2.2/javax/persistence/package-summary.html)

#### oCell
* `@ClassName` - sheet name
* `@FieldName` - column name
* `@FieldOrder` - column order
* `@FieldFormat` - column format
* `@FieldAlignment` - column alignment by horizontal or vertical properties\
(`HorizontalAlignment` or `VerticalAlignment` enumeration values)
* `@HeaderAlignment` - column header alignment by horizontal or vertical properties\
(`HorizontalAlignment` or `VerticalAlignment` enumeration values)
* `@FieldFormula` - formula column
* `@FieldExclude` - excluded column
* `@FieldConverter` - column converter

#### Jackson
* `@JsonTypeName` - sheet name
* `@JsonProperty` - column name
* `@JsonPropertyOrder` - only alphabetic order
* `@JsonProperty` - column order by *index* property
* `@JsonFormat` - column format by *pattern* property
* `@JsonIgnore` - excluded column

#### JAXB
* `@XmlRootElement` - sheet name by *name* property
* `@XmlElement` - column name by *name* property
* `@XmlAttribute` - column name by *name* property
* `@XmlAccessorOrder` - alphabetic order
* `@XmlTransient` - excluded column

#### JPA
* `@Entity` - sheet name
* `@Table` - sheet name
* `@Column` - column name by *name* property
* `@Transient` - excluded column

#### Default
Default values could be applied to POJO fields using annotations:
* `@StringValue`
* `@CharValue` - not applicable to primitive `char`
* `@BooleanValue` - not applicable to primitive `boolean`
* `@NumberValue` - not applicable to primitive types
* `@EnumValue` - string value of enum constant name
* `@DateValue` - default format is `DateTimeFormatter.ISO_INSTANT`\
or could be overridden by `@FieldFormat` or `@JsonFormat` annotations

#### Priority
All types of annotations could be mixed with one POJO.

Priority order of annotations from lower to higher: *JPA* < *JAXB* < *Jackson* < *oCell*.

#### Mapping
The library supports two types of `MappingType`:
* `STRICT` - all POJO fields must be mapped to spreadsheet columns and vice versa
* `FLEXIBLE` - some POJO fields might not be mapped to spreadsheet columns and vice versa

The default mapping type is `FLEXIBLE` 

#### Document
The library supports two types of spreadsheet documents:
* `DocumentBIFF` - *Microsoft Office Binary File Format (.xls)*
* `DocumentOOXML` - *Office Open XML File Format (.xlsx)*

#### Example
POJO mapping examples at this [GitHub](https://github.com/rushuat/ocell/tree/main/src/test/java/io/github/rushuat/ocell/model) folder.

`@FieldConverter` examples at this [GitHub](https://github.com/rushuat/ocell/tree/main/src/test/java/io/github/rushuat/ocell/field) folder.

`@FieldFormat` examples at this [Stack Overflow](https://stackoverflow.com/questions/319438/basic-excel-currency-format-with-apache-poi) link.

Value converter example:
```java
public class PercentConverter implements ValueConverter<String, Integer> {

  @Override
  public String convertInput(Integer value) {
    return value == null ? "" : value + "%";
  }

  @Override
  public Integer convertOutput(String value) {
    return value == null || value.isEmpty() ? null : Integer.valueOf(value.replaceAll("%", ""));
  }
}
```

POJO mapping example:
```java
@ClassName("POJO")
public class Model extends Base {

  @FieldOrder(0)
  @NumberValue(0)
  @FieldName("Id")
  private Long id;

  @FieldOrder(1)
  @StringValue("model")
  private String name;

  @FieldOrder(3)
  @FieldFormat("#0.00")
  @NumberValue(0.1234)
  private Double rating;

  @FieldConverter(PercentConverter.class)
  @FieldName("%")
  private Integer percent;

  @HeaderAlignment(horizontal = "center")
  @FieldAlignment(horizontal = "center")
  @BooleanValue(true)
  private Boolean enable;

  @FieldAlignment(horizontal = "left", vertical = "top")
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  private Date start;

  @DateValue("2020-01-01T11:12:13Z")
  private Date end;

  @EnumValue("NEW")
  private Status status;

  @FieldFormula
  @StringValue("CONCATENATE(2+5,\"!\")")
  private String formula;

  @FieldExclude
  private Object data;
}
```

Documents could be created using the `Documents` class:
```java
try (Document document = Documents.BIFF().create()) { ... }
try (Document document = Documents.BIFF(password).create()) { ... }
try (Document document = Documents.BIFF().mapping(MappingType.STRICT).create()) { ... }
try (Document document = Documents.BIFF(password).converter(String.class, new StringConverter()).create()) { ... }

try (Document document = Documents.OOXML().create()) { ... }
try (Document document = Documents.OOXML(password).create()) { ... }
try (Document document = Documents.OOXML().mapping(MappingType.STRICT).create()) { ... }
try (Document document = Documents.OOXML(password).converter(String.class, new StringConverter()).create()) { ... }    
```

Documents could be loaded from different sources:
```java
byte[] bytes = ...
InputStream stream = ...
String path = ...
File file = ...

List<Model> list;

try (Document document = Documents.BIFF().create()) {
  document.fromBytes(bytes);
  document.fromStream(stream);
  document.fromFile(path);
  document.fromFile(file);

  list = document.getSheet(Model.class);
  list = document.getSheet(0, Model.class);
  list = document.getSheet("Sheet1", Model.class);
}
```

Documents could be stored to different sources:
```java
byte[] bytes;
OutputStream stream = ...
String path = ...
File file = ...

List<Model> list = ...

try (Document document = Documents.OOXML().create()) {
  document.addSheet(list);
  document.addSheet("Sheet1", list);

  bytes = document.toBytes();
  document.toStream(stream);
  document.toFile(path);
  document.toFile(file);
}
```

## Future 
1. Fork version based on [FastExcel](https://github.com/dhatim/fastexcel)
2. Stream version based on [Apache POI Streaming](http://poi.apache.org/components/spreadsheet/how-to.html#sxssf)
