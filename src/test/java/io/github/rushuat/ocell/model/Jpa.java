package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.AgeConverter;
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
