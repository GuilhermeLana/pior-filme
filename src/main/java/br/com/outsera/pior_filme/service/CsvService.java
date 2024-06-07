package br.com.outsera.pior_filme.service;

import br.com.outsera.pior_filme.entity.Movie;
import br.com.outsera.pior_filme.repository.MovieRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void readCsv() {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(';')
                .build();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new ClassPathResource("movielist.csv").getInputStream()))
                .withCSVParser(csvParser)
                .build()) {
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                if (record[0].equalsIgnoreCase("year")) {
                    continue;
                }
                saveMovie(record);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private void saveMovie(String[] record) {
        Movie movie = new Movie();
        movie.setMovieYear(Integer.parseInt(record[0]));
        movie.setTitle(record[1]);
        movie.setStudios(record[2]);
        movie.setProducers(record[3]);
        movie.setWinner(record[4].equalsIgnoreCase("yes"));
        movieRepository.save(movie);
    }
}
