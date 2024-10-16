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
import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
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
@XmlRootElement(name = "User")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class Xml extends Base {

  @FieldOrder(0)
  @NumberValue(0)
  @XmlElement(name = "Id")
  private Long id;

  @FieldOrder(1)
  @StringValue("New User")
  @XmlAttribute(name = "User Name")
  private String name;

  @FieldOrder(2)
  @FieldAlignment(horizontal = "left", vertical = "top")
  @FieldFormat("yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  @XmlAttribute(name = "Date Of Birth")
  private Date dateOfBirth;

  @FieldOrder(3)
  @FieldConverter(AgeConverter.class)
  @NumberValue(18)
  @XmlAttribute(name = "Age")
  private Integer age;

  @FieldOrder(4)
  @FieldConverter(PercentConverter.class)
  @StringValue("50%")
  @XmlElement(name = "%")
  private String percent;

  @FieldFormat("#0.00")
  @NumberValue(0.1234)
  @XmlElement(name = "Rating")
  private Double rating;

  @FieldAlignment(horizontal = "center")
  @BooleanValue(true)
  private Boolean isNew;

  @XmlTransient
  private Object data;

  @HeaderAlignment(horizontal = "right")
  @FieldAlignment(vertical = "bottom")
  @DateValue("2020-01-01T11:12:13Z")
  private Date updated;

  @FieldOrder(5)
  @EnumValue("NEW")
  private Status status;
}
