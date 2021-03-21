package com.ua.rush.doc.ocell.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ua.rush.doc.ocell.annotation.BooleanValue;
import com.ua.rush.doc.ocell.annotation.DateValue;
import com.ua.rush.doc.ocell.annotation.FieldExclude;
import com.ua.rush.doc.ocell.annotation.FieldFormat;
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
@JsonTypeName("User")
@JsonPropertyOrder({"Id", "User Name", "Date Of Birth", "Age", "Rating", "isNew", "data"})
public class Json {

  @NumberValue(0)
  @JsonProperty(value = "Id", index = 0)
  private Long id;

  @StringValue("New User")
  @JsonProperty(value = "User Name", index = 1)
  private String name;

  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  @JsonProperty(value = "Date Of Birth", index = 2)
  private Date dateOfBirth;

  @NumberValue(18)
  @JsonProperty(value = "Age", index = 3)
  private Integer age;

  @FieldFormat("#.00")
  @NumberValue(0.1234)
  @JsonProperty("Rating")
  private Double rating;

  @BooleanValue(true)
  private Boolean isNew;

  @FieldExclude
  private Object data;
}
