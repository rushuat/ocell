[![Maven Central](https://img.shields.io/maven-central/v/io.github.rushuat/ocell.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.rushuat%22%20AND%20a:%22ocell%22)

# oCell
*oCell* is a library for Excel to POJO and POJO to Excel mapping based on [Apache POI](https://poi.apache.org/)

## Usage

### Java
The library is built for **JDK 11+** version usage.

### Dependency
Your project should have a dependency to the library:
```xml
    <dependency>
      <groupId>io.github.rushuat</groupId>
      <artifactId>ocell</artifactId>
      <version>0.1.2</version>
    </dependency>
```

### Code
The library supports POJO and few types of annotations to modify them:
1. [oCell](https://github.com/rushuat/ocell/tree/main/src/main/java/io/github/rushuat/ocell/annotation)
2. [Jackson](https://github.com/FasterXML/jackson-annotations)
3. [JPA](https://javaee.github.io/javaee-spec/javadocs/javax/persistence/package-summary.html)

#### oCell
* *@ClassName* - sheet name
* *@FieldName* - column name
* *@FieldFormat* - column format
* *@FieldOrder* - column order
* *@FieldExclude* - column not used
* *@FieldConverter* - column converter

#### Jackson
* *@JsonTypeName* - sheet name
* *@JsonPropertyOrder* - columns order or alphabetic order
* *@JsonProperty* - column name
* *@JsonFormat* - column format by *pattern* property
* *@JsonProperty* - column order by *index* property
* *@JsonIgnore* - column not used

#### JPA
* *@Entity* - sheet name
* *@Table* - sheet name
* *@Column* - column name by *name* property
* *@Transient* - column not used

#### Default
You are able to apply default values to POJO fields using annotations:
* *@StringValue*
* *@NumberValue*
* *@BooleanValue*
* *@DateValue* - default format is ISO_ZONED_DATE_TIME\
or could be overridden by *@FieldFormat* or *@JsonFormat* annotations

#### Priority
You are able to mix all types of annotations with one POJO.

The override priory from lower to higher: *JPA* < *Jackson* < *oCell*.

#### Example
You are able to find POJO mapping examples at this [URL](https://github.com/rushuat/ocell/tree/main/src/test/java/io/github/rushuat/ocell/model)

You are able to find *@FieldConverter* examples at this [URL](https://github.com/rushuat/ocell/tree/main/src/test/java/io/github/rushuat/ocell/field)

Documents could be loaded from different sources:
```java
    byte[] bytes = ...
    InputStream stream = ...
    String path = ...
    File file = ...

    List<Pojo> pojos;

    try (Document document = new Document()) {
      document.fromBytes(bytes);
      document.fromStream(stream);
      document.fromFile(path);
      document.fromFile(file);

      pojos = document.getSheet(Pojo.class);
      pojos = document.getSheet(0, Pojo.class);
      pojos = document.getSheet("Sheet1", Pojo.class);
    }
```

Documents could be stored to different sources:
```java
    byte[] bytes;
    OutputStream stream = ...
    String path = ...
    File file = ...

    List<Pojo> pojos = ...

    try (Document document = new Document()) {
      document.addSheet(pojos);
      document.addSheet("Sheet1", pojos);

      bytes = document.toBytes();
      document.toStream(stream);
      document.toFile(path);
      document.toFile(file);
    }
```

Documents could be protected by a password:
```java
    String password = ...
    
    try (Document document = new Document(password)) { ... }
```
## Future 
1. Fork version based on [FastExcel](https://github.com/dhatim/fastexcel)
2. Stream version based on [Apache POI Streaming](http://poi.apache.org/components/spreadsheet/how-to.html#sxssf)
