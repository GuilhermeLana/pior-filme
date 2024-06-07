package br.com.outsera.pior_filme;

import br.com.outsera.pior_filme.dto.ProducerIntervalDTO;
import br.com.outsera.pior_filme.dto.ProducersIntervalRequestDTO;
import br.com.outsera.pior_filme.entity.Movie;
import br.com.outsera.pior_filme.repository.MovieRepository;
import br.com.outsera.pior_filme.service.MovieService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    @Transactional
    @Rollback
    public void setup() {
        movieRepository.deleteAll();

        Movie movie1 = new Movie(2000, "Movie 1", "Studio 1", "Producer A", true);
        Movie movie2 = new Movie(2005, "Movie 2", "Studio 2", "Producer A", true);
        Movie movie3 = new Movie(2010, "Movie 3", "Studio 3", "Producer B", true);
        Movie movie4 = new Movie(2015, "Movie 4", "Studio 4", "Producer B, Producer A", true);

        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4));
    }

    @Test
    public void testGetProducerWithLongestInterval() {
        ProducersIntervalRequestDTO result = movieService.getProducerWithLongestInterval();

        List<ProducerIntervalDTO> longestIntervals = result.getMax();
        List<ProducerIntervalDTO> shortestIntervals = result.getMin();

        assertThat(longestIntervals).size().isEqualTo(1);
        assertThat(longestIntervals.get(0).getProducer()).isEqualTo("Producer A");
        assertThat(longestIntervals.get(0).getInterval()).isEqualTo(10); // De 2005 a 2015

        assertThat(shortestIntervals).size().isEqualTo(2);
        assertThat(shortestIntervals.get(0).getProducer()).isEqualTo("Producer A");
        assertThat(shortestIntervals.get(0).getInterval()).isEqualTo(5);
        assertThat(shortestIntervals.get(1).getProducer()).isEqualTo("Producer B");
        assertThat(shortestIntervals.get(1).getInterval()).isEqualTo(5);
    }
}
