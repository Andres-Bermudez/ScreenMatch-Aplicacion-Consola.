package com.cursospringalura.ScreenMatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Temporada(

        @JsonAlias("Season")
        Integer numeroTemporada,

        @JsonAlias("Episodes")
        List<DatosEpisodio> episodios
) {
        @Override
        public String toString() {
                return "\nNumero Temporada: " + numeroTemporada +
                       "\nEpisodios: " + episodios.size();
        }
}
