package com.ua.rush.doc.ocell;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.rush.doc.ocell.annotation.BooleanValue;
import com.ua.rush.doc.ocell.annotation.ClassName;
import com.ua.rush.doc.ocell.annotation.DateValue;
import com.ua.rush.doc.ocell.annotation.FieldFormat;
import com.ua.rush.doc.ocell.annotation.FieldName;
import com.ua.rush.doc.ocell.annotation.FiledOrder;
import com.ua.rush.doc.ocell.annotation.NumberValue;
import com.ua.rush.doc.ocell.annotation.StringValue;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ClassName("OCell")
public class Report {

  @FiledOrder(0)
  @FieldName("ID")
  private Integer id;

  @FiledOrder(1)
  @JsonProperty("USER_NAME")
  @StringValue("User Name")
  private String userName;

  @FiledOrder(2)
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  private Date dateOfBirth;

  @JsonFormat(pattern = "#.00")
  @NumberValue(0.1234)
  private Double rate;

  @BooleanValue(false)
  private Boolean isNew;
}
