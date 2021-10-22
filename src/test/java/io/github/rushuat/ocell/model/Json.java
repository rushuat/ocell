package io.github.rushuat.ocell.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.FieldConverter;
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
@JsonTypeName("User")
@JsonPropertyOrder({"Id", "User Name", "Date Of Birth", "Age", "Rating", "isNew", "data"})
public class Json {

  @NumberValue(0)
  @JsonProperty(value = "Id", index = 0)
  private Long id;

  @StringValue("New User")
  @JsonProperty(value = "User Name", index = 1)
  private String name;

  @DateValue("1991-08-24T01:02:03")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonProperty(value = "Date Of Birth", index = 2)
  private Date dateOfBirth;

  @NumberValue(18)
  @FieldConverter(AgeConverter.class)
  @JsonProperty(value = "Age", index = 3)
  private Integer age;

  @NumberValue(0.1234)
  @JsonFormat(pattern = "#.00")
  @JsonProperty("Rating")
  private Double rating;

  @BooleanValue(true)
  private Boolean isNew;

  @JsonIgnore
  private Object data;
}
