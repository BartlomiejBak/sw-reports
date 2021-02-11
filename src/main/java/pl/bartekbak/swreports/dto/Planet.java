package pl.bartekbak.swreports.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planet {
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
}
