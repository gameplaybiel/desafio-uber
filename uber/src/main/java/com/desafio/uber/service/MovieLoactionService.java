package com.desafio.uber.service;

import com.desafio.uber.model.MovieLocation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieLoactionService {

    private final WebClient webClient;

    public MovieLoactionService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://data.sfgov.org/resource/yitu-d5am.json")
                .build();
    }

    public List<MovieLocation> getAllMoviews(){
        return webClient.get()
                .retrieve()
                .bodyToFlux(MovieLocation.class)
                .collectList()
                .block();
    }

    public List<MovieLocation> filterByTitle(String query){
        return getAllMoviews().stream()
                .filter(m -> m.getTitle() != null && m.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<String> autocomplete(String prefix){
        return getAllMoviews().stream()
                .map(MovieLocation::getTitle)
                .filter(Objects::nonNull)
                .filter(t -> t.toLowerCase().startsWith(prefix.toLowerCase()))
                .distinct()
                .sorted()
                .limit(10)
                .collect(Collectors.toList());
    }
}
