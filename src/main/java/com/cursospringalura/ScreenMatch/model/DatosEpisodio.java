package com.cursospringalura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(
        @JsonAlias("Title")
        String titulo,

        @JsonAlias("Episode")
        Integer numeroEpisodio,

        @JsonAlias("imdbRating")
        String evaluacion,

        @JsonAlias("Released")
        String fechaLanzamiento
) {
        @Override
        public String toString() {
                return "\nTitulo: " + titulo +
                        "\nNumero Episodio: " + numeroEpisodio +
                        "\nEvaluacion: " + evaluacion +
                        "\nFecha de lanzamiento: " + fechaLanzamiento;
        }
}
