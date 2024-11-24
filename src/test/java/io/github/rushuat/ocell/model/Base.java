package io.github.rushuat.ocell.model;

import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.CharValue;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.FieldFormat;
import io.github.rushuat.ocell.annotation.FieldFormula;
import io.github.rushuat.ocell.annotation.FieldName;
import io.github.rushuat.ocell.annotation.FieldOrder;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Base {

  public static Integer hash;

  protected transient String password;

  @StringValue("MALE")
  String gender;

  @FieldName("Number of children")
  public byte children = 3;

  @NumberValue(55.55)
  public float threshold = (float) 77.77;

  @CharValue('$')
  public char currency = '?';

  @CharValue('Y')
  public Character signed;

  @FieldOrder(6)
  @StringValue("Jeep")
  protected String car;

  @FieldOrder(7)
  @StringValue("USA")
  private String citizen;

  @BooleanValue(true)
  private boolean isOwner;

  @FieldFormula
  @StringValue("CONCATENATE(2+5,\"!\")")
  private String formula;

  private String empty = "";

  @FieldFormat("DD/MM/YYYY")
  @FieldConverter(DateConverter.class)
  @StringValue("20140220")
  private String created;

  @NumberValue(1000)
  private Short amount;
}
