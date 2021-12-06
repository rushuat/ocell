package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.FieldAlignment;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.AgeConverter;
import io.github.rushuat.ocell.field.PercentConverter;
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
  @FieldAlignment(horizontal = "left", vertical = "top")
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  @Column(name = "Date Of Birth")
  private Date dateOfBirth;

  @FieldOrder(3)
  @FieldConverter(AgeConverter.class)
  @NumberValue(18)
  @Column(name = "Age")
  private Integer age;

  @FieldOrder(4)
  @FieldConverter(PercentConverter.class)
  @StringValue("50%")
  @Column(name = "%")
  private String percent;

  @FieldFormat("#0.00")
  @NumberValue(0.1234)
  @Column(name = "Rating")
  private Double rating;

  @FieldAlignment(horizontal = "center")
  @BooleanValue(true)
  private Boolean isNew;

  @Transient
  private Object data;

  @FieldAlignment(vertical = "bottom")
  @DateValue("2020-01-01T11:12:13Z")
  private Date updated;
}
