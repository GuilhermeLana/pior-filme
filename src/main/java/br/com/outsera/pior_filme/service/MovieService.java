package br.com.outsera.pior_filme.service;

import br.com.outsera.pior_filme.dto.ProducerIntervalDTO;
import br.com.outsera.pior_filme.dto.ProducersIntervalRequestDTO;
import br.com.outsera.pior_filme.entity.Movie;
import br.com.outsera.pior_filme.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public ProducersIntervalRequestDTO getProducerWithLongestInterval() {
        List<Movie> winnerMovies = getWinnerMovies();

        Map<String, List<Integer>> producerWins = new HashMap<>();
        List<ProducerIntervalDTO> producersDTO = new ArrayList<>();

        getProducers(winnerMovies, producerWins);

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> wins = entry.getValue();
            Collections.sort(wins);
            for (int i = 1; i < wins.size(); i++) {
                int interval = wins.get(i) - wins.get(i - 1);
                ProducerIntervalDTO producer = new ProducerIntervalDTO(entry.getKey(), interval, wins.get(i - 1), wins.get(i));
                producersDTO.add(producer);
            }
        }

        producersDTO.sort(Comparator.comparing(ProducerIntervalDTO::getInterval));
        int minInterval = producersDTO.get(0).getInterval();
        List<ProducerIntervalDTO> producersWithShortestInterval = producersDTO.stream().filter(producer -> producer.getInterval() == minInterval).toList();

        producersDTO.sort(Comparator.comparing(ProducerIntervalDTO::getInterval).reversed());
        int maxInterval = producersDTO.get(0).getInterval();
        List<ProducerIntervalDTO> producersWithLongestInterval = producersDTO.stream().filter(producer -> producer.getInterval() == maxInterval).toList();

        return new ProducersIntervalRequestDTO(producersWithShortestInterval, producersWithLongestInterval);
    }

    private List<Movie> getWinnerMovies() {
        List<Movie> winnerMovies = movieRepository.findByWinnerTrue();
        return winnerMovies;
    }

    private static void getProducers(List<Movie> winnerMovies, Map<String, List<Integer>> producerWins) {
        for (Movie movie : winnerMovies) {
            String producersString = movie.getProducers();
            producersString = producersString.replaceAll("\\s+and\\s+", ", ");
            String[] producers = producersString.split(",\\s*");
            for (String producer : producers) {
                producerWins.computeIfAbsent(producer, k -> new ArrayList<>()).add(movie.getMovieYear());
            }
        }
    }
}
