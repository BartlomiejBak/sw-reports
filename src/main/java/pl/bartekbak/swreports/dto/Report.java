package pl.bartekbak.swreports.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @JsonProperty("report_id")
    @Column(name = "report_id")
    private Long reportId;
    @JsonProperty("query_criteria_character_phrase")
    private String characterQueryCriteria;
    @JsonProperty("query_criteria_planet_name")
    private String planetQueryCriteria;
    @JsonProperty("result")
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "report_result", joinColumns = @JoinColumn(name = "report_id"),
    inverseJoinColumns = @JoinColumn(name = "result_id"))
    @Builder.Default
    private List<Result> resultList = new ArrayList<>();
}
