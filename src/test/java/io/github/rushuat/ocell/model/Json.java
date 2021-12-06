package io.github.rushuat.ocell.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.rushuat.ocell.annotation.BooleanValue;
import io.github.rushuat.ocell.annotation.DateValue;
import io.github.rushuat.ocell.annotation.FieldAlignment;
import io.github.rushuat.ocell.annotation.FieldConverter;
import io.github.rushuat.ocell.annotation.NumberValue;
import io.github.rushuat.ocell.annotation.StringValue;
import io.github.rushuat.ocell.field.AgeConverter;
import io.github.rushuat.ocell.field.PercentConverter;
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
@JsonPropertyOrder(
    {"Id", "User Name", "Date Of Birth", "Age", "%", "Rating", "isNew", "data", "updated"}
)
public class Json {

  @NumberValue(0)
  @JsonProperty(value = "Id", index = 0)
  private Long id;

  @StringValue("New User")
  @JsonProperty(value = "User Name", index = 1)
  private String name;

  @FieldAlignment(horizontal = "left", vertical = "top")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @DateValue("1991-08-24T01:02:03")
  @JsonProperty(value = "Date Of Birth", index = 2)
  private Date dateOfBirth;

  @FieldConverter(AgeConverter.class)
  @NumberValue(18)
  @JsonProperty(value = "Age", index = 3)
  private Integer age;

  @FieldConverter(PercentConverter.class)
  @StringValue("50%")
  @JsonProperty(value = "%", index = 4)
  private String percent;

  @JsonFormat(pattern = "#0.00")
  @NumberValue(0.1234)
  @JsonProperty("Rating")
  private Double rating;

  @FieldAlignment(horizontal = "center")
  @BooleanValue(true)
  private Boolean isNew;

  @JsonIgnore
  private Object data;

  @FieldAlignment(vertical = "bottom")
  @DateValue("2020-01-01T11:12:13Z")
  private Date updated;
}
