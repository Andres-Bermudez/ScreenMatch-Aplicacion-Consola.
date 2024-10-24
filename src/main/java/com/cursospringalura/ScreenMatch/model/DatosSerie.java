package com.cursospringalura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(

        @JsonAlias("Title")
        String titulo,

        @JsonAlias("totalSeasons")
        Integer totalTemporadas,

        @JsonAlias("imdbRating")
        String evaluacion
) {
        @Override
        public String toString() {
                return "\nDATOS GENERALES DE LA SERIE:" +
                       "\nTitulo: " + titulo +
                       "\nTotalTemporadas: " + totalTemporadas +
                       "\nEvaluacion: " + evaluacion;
        }
}
