[![maven-central](https://img.shields.io/maven-central/v/io.github.rushuat/ocell.svg?color=blue)](https://search.maven.org/search?q=g:%22io.github.rushuat%22%20AND%20a:%22ocell%22)
[![javadoc](https://javadoc.io/badge2/io.github.rushuat/ocell/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.rushuat/ocell)
[![license](https://img.shields.io/github/license/rushuat/ocell.svg?color=blue)](https://www.apache.org/licenses/LICENSE-2.0.txt)

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
      <version>0.1.7</version>
    </dependency>
```

### Code
The library supports POJOs and few types of annotations to customize them:
1. [oCell](https://github.com/rushuat/ocell/tree/main/src/main/java/io/github/rushuat/ocell/annotation)
2. [Jackson](https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations)
3. [JAXB](https://javadoc.io/doc/javax.xml.bind/jaxb-api/2.3.1/javax/xml/bind/annotation/package-summary.html)
4. [JPA](https://javadoc.io/static/javax.persistence/javax.persistence-api/2.2/javax/persistence/package-summary.html)

#### oCell
* *@ClassName* - sheet name
* *@FieldName* - column name
* *@FieldFormat* - column format
* *@FieldAlignment* - column alignment by horizontal or vertical properties\
(*HorizontalAlignment* or *VerticalAlignment* enumeration values)
* *@HeaderAlignment* - column header alignment by horizontal or vertical properties\
(*HorizontalAlignment* or *VerticalAlignment* enumeration values)
* *@FieldOrder* - column order
* *@FieldExclude* - column not used
* *@FieldConverter* - column converter

#### Jackson
* *@JsonTypeName* - sheet name
* *@JsonPropertyOrder* - only alphabetic order
* *@JsonProperty* - column name
* *@JsonFormat* - column format by *pattern* property
* *@JsonProperty* - column order by *index* property
* *@JsonIgnore* - column not used

#### JAXB
* *@XmlRootElement* - sheet name by *name* property
* *@XmlAccessorOrder* - alphabetic order
* *@XmlElement* - column name by *name* property
* *@XmlAttribute* - column name by *name* property
* *@XmlTransient* - column not used

#### JPA
* *@Entity* - sheet name
* *@Table* - sheet name
* *@Column* - column name by *name* property
* *@Transient* - column not used

#### Default
Default values could be applied to POJO fields using annotations:
* *@StringValue*
* *@CharValue* - not applicable to primitive *char*
* *@BooleanValue* - not applicable to primitive *boolean*
* *@NumberValue* - not applicable to primitive types
* *@EnumValue* - string value of enum constant name
* *@DateValue* - default format is *DateTimeFormatter.ISO_INSTANT*\
or could be overridden by *@FieldFormat* or *@JsonFormat* annotations

#### Priority
All types of annotations could be mixed with one POJO.

Priority order of annotations from lower to higher: *JPA* < *JAXB* < *Jackson* < *oCell*.

#### Document
The library supports two types of spreadsheet documents:
* *DocumentBIFF* - Microsoft Office Binary File Format (.xls)
* *DocumentOOXML* - Office Open XML File Format (.xlsx)

#### Example
POJO mapping examples at this [URL](https://github.com/rushuat/ocell/tree/main/src/test/java/io/github/rushuat/ocell/model)

*@FieldConverter* examples at this [URL](https://github.com/rushuat/ocell/tree/main/src/test/java/io/github/rushuat/ocell/field)

*@FieldFormat* examples at this [URL](https://stackoverflow.com/questions/319438/basic-excel-currency-format-with-apache-poi)

Documents could be loaded from different sources:
```java
    byte[] bytes = ...
    InputStream stream = ...
    String path = ...
    File file = ...

    List<Pojo> pojos;

    try (Document document = new DocumentOOXML()) {
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

    try (Document document = new DocumentOOXML()) {
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
    
    try (Document document = new DocumentOOXML(password)) { ... }
```
## Future 
1. Fork version based on [FastExcel](https://github.com/dhatim/fastexcel)
2. Stream version based on [Apache POI Streaming](http://poi.apache.org/components/spreadsheet/how-to.html#sxssf)
