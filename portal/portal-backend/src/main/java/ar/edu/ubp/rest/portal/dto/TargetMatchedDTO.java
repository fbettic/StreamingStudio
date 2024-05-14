package ar.edu.ubp.rest.portal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetMatchedDTO {
    private Integer id;
    private List<Integer> targets;
}
