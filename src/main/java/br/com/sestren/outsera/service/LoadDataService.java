package br.com.sestren.outsera.service;

import br.com.sestren.outsera.database.entity.*;
import br.com.sestren.outsera.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class LoadDataService {

    private final MovieRepository movieRepository;
    private final StudioRepository studioRepository;
    private final MovieStudioRepository movieStudioRepository;
    private final ProducerRepository producerRepository;
    private final MovieProducerRepository movieProducerRepository;

    @Value("${app.movie.file}")
    private String filenameMovies;

    @Autowired
    public LoadDataService(MovieRepository movieRepository, StudioRepository studioRepository, MovieStudioRepository movieStudioRepository, ProducerRepository producerRepository, MovieProducerRepository movieProducerRepository) {
        this.movieRepository = movieRepository;
        this.studioRepository = studioRepository;
        this.movieStudioRepository = movieStudioRepository;
        this.producerRepository = producerRepository;
        this.movieProducerRepository = movieProducerRepository;
    }

    public void loadData() throws IOException {
        InputStream csvFile = this.getClass().getClassLoader().getResourceAsStream(filenameMovies);
        if (csvFile != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile))) {
                reader.lines().skip(1).forEach(this::fromFile);
            }
        }
    }

    private void fromFile(String line) {
        Movie movie = new Movie();

        String[] parts = line.split(";");
        movie.setYear(Integer.parseInt(parts[0].trim()));
        movie.setTitle(parts[1].trim());
        movie.setWinner(treatWinner(parts));

        movie = movieRepository.save(movie);

        treatStudios(movie, parts[2].trim());
        treatProducers(movie, parts[3].trim());
    }

    private Boolean treatWinner(String[] parts) {
        if (parts.length > 4) {
            return parts[4].trim().equalsIgnoreCase("yes");
        }
        return false;
    }

    private void treatProducers(Movie movie, String line) {
        String[] producersNames = line.split(",| and ");
        for (String producerName : producersNames) {
            producerName = producerName.trim();

            if (!producerName.isEmpty()) {
                Producer producer = this.producerRepository.findByName(producerName).orElse(null);
                if (producer == null) {
                    producer = new Producer();
                    producer.setName(producerName);
                    producer = producerRepository.save(producer);
                }
                MovieProducer movieProducer = new MovieProducer();
                movieProducer.setProducer(producer);
                movieProducer.setMovie(movie);
                movieProducerRepository.save(movieProducer);
            }
        }
    }

    private void treatStudios(Movie movie, String line) {
        String[] studiosNames = line.split(",| and ");
        for (String studioName : studiosNames) {
            studioName = studioName.trim();

            if (!studioName.isEmpty()) {
                Studio studio = this.studioRepository.findByName(studioName).orElse(null);
                if (studio == null) {
                    studio = new Studio();
                    studio.setName(studioName);
                    studio = studioRepository.save(studio);
                }
                MovieStudio movieStudio = new MovieStudio();
                movieStudio.setStudio(studio);
                movieStudio.setMovie(movie);
                movieStudioRepository.save(movieStudio);
            }
        }
    }
}
