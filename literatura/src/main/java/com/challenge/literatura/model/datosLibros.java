package com.challenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record datosLibros(@JsonAlias("id") Long id,
                          @JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<datosAutores> autores,
                          @JsonAlias("languages") List<String> lenguajes,
                          @JsonAlias("copyright") String copyright,
                          @JsonAlias("download_count") Integer downloadCount) {
}
