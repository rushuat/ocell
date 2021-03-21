package com.ua.rush.doc.ocell.model;

import com.ua.rush.doc.ocell.annotation.BooleanValue;
import com.ua.rush.doc.ocell.annotation.ClassName;
import com.ua.rush.doc.ocell.annotation.DateValue;
import com.ua.rush.doc.ocell.annotation.FieldExclude;
import com.ua.rush.doc.ocell.annotation.FieldFormat;
import com.ua.rush.doc.ocell.annotation.FieldName;
import com.ua.rush.doc.ocell.annotation.FieldOrder;
import com.ua.rush.doc.ocell.annotation.NumberValue;
import com.ua.rush.doc.ocell.annotation.StringValue;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ClassName("User")
public class Pojo {

  @FieldOrder(0)
  @NumberValue(0)
  @FieldName("Id")
  private Long id;

  @FieldOrder(1)
  @StringValue("New User")
  @FieldName("User Name")
  private String name;

  @FieldOrder(2)
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  @FieldName("Date Of Birth")
  private Date dateOfBirth;

  @FieldOrder(3)
  @NumberValue(18)
  @FieldName("Age")
  private Integer age;

  @FieldFormat("#.00")
  @NumberValue(0.1234)
  @FieldName("Rating")
  private Double rating;

  @BooleanValue(true)
  private Boolean isNew;

  @FieldExclude
  private Object data;
}
