package pl.bartekbak.swreports.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @JsonIgnore
    private int resultId;
    @JsonProperty("film_id")
    private int filmId;
    @JsonProperty("film_name")
    private String filmName;
    @JsonProperty("character_id")
    private int characterId;
    @JsonProperty("character_name")
    private String characterName;
    @JsonProperty("planet_id")
    private int planetId;
    @JsonProperty("planet_name")
    private String planetName;


}
