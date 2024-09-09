package br.com.sestren.outsera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AwardsDTO {
    private List<ProducerIntervalDTO> min;
    private List<ProducerIntervalDTO> max;
}
