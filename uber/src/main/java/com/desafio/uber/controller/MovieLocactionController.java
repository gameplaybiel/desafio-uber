package com.desafio.uber.controller;

import com.desafio.uber.model.MovieLocation;
import com.desafio.uber.service.MovieLoactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieLocactionController {

    private final MovieLoactionService service;

    public MovieLocactionController(MovieLoactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<MovieLocation> getAll(@RequestParam Optional<String> title){
        return title.map(service::filterByTitle)
                .orElseGet(service::getAllMoviews);
    }

    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam("q") String prefix){
        return service.autocomplete(prefix);
    }
}
