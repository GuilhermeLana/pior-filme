package br.com.outsera.pior_filme;

import br.com.outsera.pior_filme.dto.ProducerIntervalDTO;
import br.com.outsera.pior_filme.dto.ProducersIntervalRequestDTO;
import br.com.outsera.pior_filme.repository.MovieRepository;
import br.com.outsera.pior_filme.service.CsvService;
import br.com.outsera.pior_filme.service.MovieService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    @Transactional
    @Rollback
    public void setup() {
        movieRepository.deleteAll();
        csvService.readCsv();
    }

    @Test
    public void testGetProducersInterval() {
        ProducersIntervalRequestDTO result = movieService.getProducersInterval();

        List<ProducerIntervalDTO> longestIntervals = result.getMax();
        List<ProducerIntervalDTO> shortestIntervals = result.getMin();

        assertThat(shortestIntervals).size().isEqualTo(1);
        assertThat(shortestIntervals.get(0).getInterval()).isEqualTo(1);

        assertThat(longestIntervals).size().isEqualTo(1);
        assertThat(longestIntervals.get(0).getInterval()).isEqualTo(13);
    }
}
