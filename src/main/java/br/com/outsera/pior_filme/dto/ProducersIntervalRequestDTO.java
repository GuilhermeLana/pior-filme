package br.com.outsera.pior_filme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProducersIntervalRequestDTO {
    private List<ProducerIntervalDTO> min;
    private List<ProducerIntervalDTO> max;
}
