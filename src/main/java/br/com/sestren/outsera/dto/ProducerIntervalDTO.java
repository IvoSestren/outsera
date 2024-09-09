package br.com.sestren.outsera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProducerIntervalDTO {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;
}
