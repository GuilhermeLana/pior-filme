package br.com.outsera.pior_filme.controller;

import br.com.outsera.pior_filme.dto.ProducersIntervalRequestDTO;
import br.com.outsera.pior_filme.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieRestController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/producers-interval")
    public ProducersIntervalRequestDTO getProducersInterval(){
        return movieService.getProducerWithLongestInterval();
    }

}
