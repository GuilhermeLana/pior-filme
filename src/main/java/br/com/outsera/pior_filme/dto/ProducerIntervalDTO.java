package br.com.outsera.pior_filme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProducerIntervalDTO {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;
}
