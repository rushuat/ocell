package com.ua.rush.doc.ocell;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.rush.doc.ocell.annotation.BooleanValue;
import com.ua.rush.doc.ocell.annotation.ClassName;
import com.ua.rush.doc.ocell.annotation.DateValue;
import com.ua.rush.doc.ocell.annotation.FieldFormat;
import com.ua.rush.doc.ocell.annotation.FieldName;
import com.ua.rush.doc.ocell.annotation.FieldOrder;
import com.ua.rush.doc.ocell.annotation.NumberValue;
import com.ua.rush.doc.ocell.annotation.StringValue;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ClassName("OCell")
public class Report {

  @FieldOrder(0)
  @FieldName("ID")
  private Integer id;

  @FieldOrder(1)
  @JsonProperty("USER_NAME")
  @StringValue("User Name")
  private String userName;

  @FieldOrder(2)
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  private Date dateOfBirth;

  @JsonFormat(pattern = "#.00")
  @NumberValue(0.1234)
  private Double rate;

  @BooleanValue(false)
  private Boolean isNew;
}
