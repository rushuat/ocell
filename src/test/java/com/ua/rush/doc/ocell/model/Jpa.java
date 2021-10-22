package com.ua.rush.doc.ocell.model;

import com.ua.rush.doc.ocell.annotation.BooleanValue;
import com.ua.rush.doc.ocell.annotation.DateValue;
import com.ua.rush.doc.ocell.annotation.FieldConverter;
import com.ua.rush.doc.ocell.annotation.FieldFormat;
import com.ua.rush.doc.ocell.annotation.FieldOrder;
import com.ua.rush.doc.ocell.annotation.NumberValue;
import com.ua.rush.doc.ocell.annotation.StringValue;
import com.ua.rush.doc.ocell.field.AgeConverter;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "User")
public class Jpa {

  @FieldOrder(0)
  @NumberValue(0)
  @Column(name = "Id")
  private Long id;

  @FieldOrder(1)
  @StringValue("New User")
  @Column(name = "User Name")
  private String name;

  @FieldOrder(2)
  @DateValue("1991-08-24T01:02:03")
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @Column(name = "Date Of Birth")
  private Date dateOfBirth;

  @FieldOrder(3)
  @NumberValue(18)
  @FieldConverter(AgeConverter.class)
  @Column(name = "Age")
  private Integer age;

  @NumberValue(0.1234)
  @FieldFormat("#.00")
  @Column(name = "Rating")
  private Double rating;

  @BooleanValue(true)
  private Boolean isNew;

  @Transient
  private Object data;
}
