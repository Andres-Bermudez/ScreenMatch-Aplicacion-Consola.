package com.cursospringalura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(

        @JsonAlias("Title")
        String titulo,

        @JsonAlias("Year")
        String year,

        @JsonAlias("Rated")
        String Calificada,

        @JsonAlias("Released")
        String fechaPublicacion,

        @JsonAlias("Runtime")
        String duracion,

        @JsonAlias("Genre")
        String genero,

        @JsonAlias("Director")
        String director,

        @JsonAlias("Writer")
        String escritor,

        @JsonAlias("Actors")
        String actores,

        @JsonAlias("Plot")
        String sinopsis,

        @JsonAlias("Language")
        String lenguaje,

        @JsonAlias("Country")
        String pais,

        @JsonAlias("Awards")
        String premios,

        @JsonAlias("Poster")
        String imagen,

        @JsonAlias("totalSeasons")
        Integer totalTemporadas,

        @JsonAlias("imdbRating")
        String evaluacion,

        @JsonAlias("imdbVotes")
        String totalVotos
) {
        @Override
        public String toString() {
                return "\nDATOS DE LA SERIE: " +
                        "\nTitulo: " + titulo +
                        "\nAño: " + year +
                        "\nCalificada: " + Calificada +
                        "\nFecha de publicacion: " + fechaPublicacion +
                        "\nDuracion: " + duracion +
                        "\nGenero: " + genero +
                        "\nDirector: " + director +
                        "\nEscritor: " + escritor +
                        "\nActores: " + actores +
                        "\nSinopsis: " + sinopsis +
                        "\nLenguaje: " + lenguaje +
                        "\nPais: " + pais +
                        "\nPremios: " + premios +
                        "\nImagen: " + imagen +
                        "\nTotal de temporadas: " + totalTemporadas +
                        "\nEvaluacion: " + evaluacion +
                        "\nTotal de votos: " + totalVotos;
        }
}
