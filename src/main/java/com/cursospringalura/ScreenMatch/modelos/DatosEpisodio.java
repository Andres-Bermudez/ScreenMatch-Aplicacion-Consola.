package com.cursospringalura.ScreenMatch.modelos;

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
                return "\nNumero Episodio: " + numeroEpisodio +
                        "\nTitulo: " + titulo +
                        "\nFecha de lanzamiento: " + fechaLanzamiento +
                        "\nEvaluacion: " + evaluacion;
        }
}
