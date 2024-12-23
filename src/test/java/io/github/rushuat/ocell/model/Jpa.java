package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.EnumValue;
import io.github.rushuat.ocell.annotation.FieldAlignment;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.HeaderAlignment;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.AgeConverter;
import io.github.rushuat.ocell.field.PercentConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "User")
@Table(name = "User")
public class Jpa extends Base {

  @FieldOrder(0)
  @NumberValue(0)
  @Id
  @Column(name = "Id")
  private Long id;

  @FieldOrder(1)
  @StringValue("New User")
  @Column(name = "User Name")
  private String name;

  @FieldOrder(2)
  @FieldAlignment(horizontal = "left", vertical = "top")
  @DateValue(value = "1991-08-24T01:02:03", format = "yyyy-MM-dd'T'HH:mm:ss")
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

  @HeaderAlignment(horizontal = "right")
  @FieldAlignment(vertical = "bottom")
  @DateValue("2020-01-01T11:12:13Z")
  private Date updated;

  @FieldOrder(5)
  @EnumValue("NEW")
  private Status status;
}
