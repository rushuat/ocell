package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.ClassName;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldExclude;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldName;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.AgeConverter;
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
  @DateValue("1991-08-24T01:02:03")
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @FieldName("Date Of Birth")
  private Date dateOfBirth;

  @FieldOrder(3)
  @NumberValue(18)
  @FieldConverter(AgeConverter.class)
  @FieldName("Age")
  private Integer age;

  @NumberValue(0.1234)
  @FieldFormat("#.00")
  @FieldName("Rating")
  private Double rating;

  @BooleanValue(true)
  private Boolean isNew;

  @FieldExclude
  private Object data;
}
