package pl.bartekbak.swreports.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    @JsonProperty("query_criteria_character_phrase")
    @Builder.Default
    private String characterQueryCriteria = "";
    @JsonProperty("query_criteria_planet_name")
    @Builder.Default
    private String planetQueryCriteria = "";
}
